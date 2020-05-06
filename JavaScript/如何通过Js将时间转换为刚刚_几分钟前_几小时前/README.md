前言
--

今天弄了一天的评论模块，我们常见的评论或者朋友圈都能看到的发送时间是刚刚，几分钟前，几小时前，所以我在做评论模块的时候也想进行这样的转换，其实转换的方法也挺简单的，就是获取到评论发送的时间戳和当前的时间戳进行对比，然后在除分、时、天、周、月所对应的时间戳，就能得到我们想要的效果了，最终效果如下所示

![](http://image.moguit.cn/1578831128367.png)

代码
--

首先需要传入的是我们评论的时间，格式如：2020-01-12 20:10:15

          /**
           * dateTimeStamp是评论的发送时间   2020-01-12 20:10:15 这样的形式
           * @param dateTimeStamp
           * @returns {string}
           */
          timeAgo(dateTimeStamp) {
            let result = "";
            let minute = 1000 * 60;      //把分，时，天，周，半个月，一个月用毫秒表示
            let hour = minute * 60;
            let day = hour * 24;
            let week = day * 7;
            let halfamonth = day * 15;
            let month = day * 30;
            let now = new Date().getTime();   //获取当前时间毫秒
    
            dateTimeStamp = dateTimeStamp.substring(0, 18);
            //必须把日期'-'转为'/'
            dateTimeStamp = dateTimeStamp.replace(/-/g, '/');
            let timestamp = new Date(dateTimeStamp).getTime();
    
            let diffValue = now - timestamp;//时间差
    
            if (diffValue < 0) {
              return result;
            }
            let minC = diffValue / minute;  //计算时间差的分，时，天，周，月
            let hourC = diffValue / hour;
            let dayC = diffValue / day;
            let weekC = diffValue / week;
            let monthC = diffValue / month;
            if (monthC >= 1 && monthC <= 3) {
              result = " " + parseInt(monthC) + "月前"
            } else if (weekC >= 1 && weekC <= 3) {
              result = " " + parseInt(weekC) + "周前"
            } else if (dayC >= 1 && dayC <= 6) {
              result = " " + parseInt(dayC) + "天前"
            } else if (hourC >= 1 && hourC <= 23) {
              result = " " + parseInt(hourC) + "小时前"
            } else if (minC >= 1 && minC <= 59) {
              result = " " + parseInt(minC) + "分钟前"
            } else if (diffValue >= 0 && diffValue <= minute) {
              result = "刚刚"
            } else {
              let datetime = new Date();
              datetime.setTime(dateTimeStamp);
              let Nyear = datetime.getFullYear();
              let Nmonth = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
              let Ndate = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
              let Nhour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
              let Nminute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
              let Nsecond = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
              result = Nyear + "-" + Nmonth + "-" + Ndate
            }
            return result;
          },