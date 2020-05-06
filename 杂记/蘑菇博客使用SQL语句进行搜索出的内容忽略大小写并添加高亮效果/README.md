前言
--

前阵子一直在改造蘑菇博客的搜索模块，因为之前是使用的Solr搜索，每次搜索返回的时候，会自带高亮字段，后面改成SQL的方式实现搜索后，也想着能够增加高亮字段，因此就想着我们通过like关键字SQL查询出来后，能够在给关键字段添加一个标签

    <span style = 'color:red'></span>

这样就能够让关键字能够出现想要的高亮效果了

但是这样存在一个问题就是，我们如何能够忽略大小写，给关键字添加高亮呢

实现
--

 其实我们的算法实现原理是比较简单的：

*   给定需要高亮的字段 str，以及关键字keyword
*   我们将str字段使用 str.toLowerCase() 方法，全部转换成小写，并将该字段定义为 lowerCaseStr
*   同理，也将keyword转换为小写， 定义为 lowerCaseKeyword
*   然后使用 lowerCaseStr.split(lowerCaseKeyword) 将字符串切割成多份
*   然后我们就需要记录每个切割后的字符串的开始角标和结束角标
*   然后通过角标，从原始的字符串中截取字符串str，存储在list中
*   最后通过循环逐个添加关键字代码

完整代码
----

        /**
         * 添加高亮
         * @param str
         * @param keyword
         * @return
         */
        private String getHitCode(String str , String keyword) {
    
            if(StringUtils.isEmpty(keyword) || StringUtils.isEmpty(str)) {
                return str;
            }
    
            String lowerCaseStr = str.toLowerCase();
            String lowerKeyword = keyword.toLowerCase();
            String [] lowerCaseArray = lowerCaseStr.split(lowerKeyword);
    
            Boolean isEndWith = lowerCaseStr.endsWith(lowerKeyword);
    
            // 计算分割后的字符串位置
            Integer count = 0;
            List<Map<String, Integer>> list = new ArrayList<>();
            List<Map<String, Integer>> keyList = new ArrayList<>();
            for(int a=0; a<lowerCaseArray.length; a++) {
    
                // 将切割出来的存储map
                Map<String, Integer> map = new HashMap<>();
                Map<String, Integer> keyMap = new HashMap<>();
                map.put("startIndex", count);
                Integer len = lowerCaseArray[a].length();
                count += len;
                map.put("endIndex", count);
                list.add(map);
    
                if(a < lowerCaseArray.length -1 || isEndWith) {
                    // 将keyword存储map
                    keyMap.put("startIndex", count);
                    count += keyword.length();
                    keyMap.put("endIndex", count);
                    keyList.add(keyMap);
                }
    
            }
    
            // 截取切割对象
            List<String> arrayList = new ArrayList<>();
            for(Map<String, Integer> item : list) {
                Integer start = item.get("startIndex");
                Integer end = item.get("endIndex");
                String itemStr = str.substring(start, end);
                arrayList.add(itemStr);
            }
    
            // 截取关键字
            List<String> keyArrayList = new ArrayList<>();
            for(Map<String, Integer> item : keyList) {
                Integer start = item.get("startIndex");
                Integer end = item.get("endIndex");
                String itemStr = str.substring(start, end);
                keyArrayList.add(itemStr);
            }
    
            String startStr = "<span style = 'color:red'>";
            String endStr = "</span>";
            StringBuffer sb = new StringBuffer();
    
            for(int a=0; a<arrayList.size(); a++) {
    
                sb.append(arrayList.get(a));
    
                if(a < arrayList.size() - 1 || isEndWith) {
                    sb.append(startStr);
                    sb.append(keyArrayList.get(a));
                    sb.append(endStr);
                }
            }
    
            return sb.toString();
        }

最终效果
----

从下图我们能够看到，搜索nginx后，能够把大写开头的Nginx，和小写的nginx都能够显示高亮

![](http://image.moguit.cn/98a2a223f0334e67a68dfbea88d8810c)