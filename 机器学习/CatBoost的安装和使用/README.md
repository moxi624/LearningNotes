# CatBoost的安装和使用

## 前言

CatBoost算法也是GBDT家族中的一种，是由俄罗斯大兄弟于2017年发表出来的[论文](https://arxiv.org/pdf/1706.09516.pdf)，它具备如下的优点：

- 它自动采用特殊的方式处理**类别型特征（categorical features）**。首先对categorical features做一些统计，计算某个类别特征（category）出现的频率，之后加上超参数，生成新的数值型特征（numerical features）。这也是我在这里介绍这个算法最大的motivtion，有了catboost，再也**不用手动处理类别型特征了。**
- catboost还使用了**组合类别特征**，可以利用到特征之间的联系，这极大的**丰富了特征维度**。
- catboost的基模型采用的是**对称树**，同时计算leaf-value方式和传统的boosting算法也不一样，传统的boosting算法计算的是平均数，而catboost在这方面做了优化采用了其他的算法，这些改进都能**防止模型过拟合**。

## 安装

下面我们使用conda进行安装，首先需要删除之前的 `.condarc` 文件，一般在 `C:\Users\Administrator` 目录

![image-20200520181910922](images/image-20200520181910922.png)

然后需要配置国内镜像源

```
conda config --add channels https://mirrors.ustc.edu.cn/anaconda/pkgs/free/
conda config --add channels https://mirrors.ustc.edu.cn/anaconda/cloud/conda-forge/
conda config --add channels https://mirrors.ustc.edu.cn/anaconda/cloud/msys2/
conda config --set show_channel_urls yes
```

配置成功后，在Anaconda import中使用conda命令进行安装

```
conda install catboost
```

## 使用

具体使用，我以一个辍学预测二分类问题案例来进行说明，首先我们引入包

```python
from catboost import CatBoostClassifier
import pandas as pd
import numpy as np
```

然后在导入我们的数据集

```python
subset_train = pd.read_csv('./GaussianNB/train.csv')
subset_test = pd.read_csv('./GaussianNB/test.csv')
x_train = subset_train.drop(["enrollment_id","result"],axis=1)
y_train = subset_train['result']
x_test = subset_test.drop(["enrollment_id","result"],axis=1)
y_test = subset_test['result']
```

配置我们的CatBoost分类器

```
categorical_features_indices = np.where(x_train.dtypes != np.float)[0]
model = CatBoostClassifier(iterations=100, depth=5,cat_features=categorical_features_indices,learning_rate=0.5, loss_function='Logloss',
                            logging_level='Verbose')
```

开始模型的训练

```
model.fit(x_train,y_train,eval_set=(x_test, y_test),plot=True)
```

预测数据的输出，同时输出到指定的目录下

```
pred = (model.predict(x_test)).tolist() ## predicting the output on the test data
catBoost_result = pd.DataFrame(pred)
catBoost_result.to_csv('./GaussianNB/result/catBoostResult.csv', index = False)
catBoost_result.shape
```

进行模型精度评判

```
noOfEnrollments = 24108
# 求出模型的百分精度
comp = [1 if pred[i] == int(y_test[i]) else 0 for i in range(noOfEnrollments)]
sum(comp)/len(y_test) * 100
```

