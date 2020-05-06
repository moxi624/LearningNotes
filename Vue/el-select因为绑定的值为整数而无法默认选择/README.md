前言
--

今天在做蘑菇博客数据字典这块遇到一个问题，就是el-select绑定的值为整数而无法默认选择的问题，它会直接显示数字，而不是选择列表中的某个选项，这个问题仅仅在我们绑定的值是Int类型的时候，才会出现

![](http://image.moguit.cn/4058b9648d3f4abebc56112852aab25d)

代码如下所示

            <el-form-item label="菜单等级" :label-width="formLabelWidth" required>
              <el-select v-model="form.menuLevel" size="small" placeholder="请选择">
                <el-option
                  v-for="item in menuLevelDictList"
                  :key="item.uid"
                  :label="item.dictLabel"
                  :value="(item.dictValue)"
                ></el-option>
              </el-select>
            </el-form-item>

这是因为 v-model 绑定的 form.menuLevel没有自动将Integer类型转为String类型，其实解决思路也比较清晰，就是在 :value部分，将原来的string类型，通过 parseInt() 方法转换为int类型即可，代码如下所示：

            <el-form-item label="菜单等级" :label-width="formLabelWidth" required>
              <el-select v-model="form.menuLevel" size="small" placeholder="请选择">
                <el-option
                  v-for="item in menuLevelDictList"
                  :key="item.uid"
                  :label="item.dictLabel"
                  :value="parseInt(item.dictValue)"
                ></el-option>
              </el-select>
            </el-form-item>

我们看最后的效果图，发现能够正常显示了：

![](http://image.moguit.cn/34240f6b0a44456bbd8b3867454b22f2)