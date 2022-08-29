## Overview

| Method | Path          | Description              |
| ------ | ------------- | ------------------------ |
| GET    | `/action`     | 获取用户行为分析数据     |
| GET    | `/age`        | 获取用户年龄分析数据     |
| GET    | `/category`   | 获取商品类别分析数据     |
| GET    | `/gender`     | 获取用户性别分析数据     |
| GET    | `/prediction` | 获取回头客预测结果       |
| GET    | `/province`   | 获取收货地址省份分析数据 |
| POST   | `/action/run`     | 开始分析用户行为     |
| POST    | `/age/run`        | 开始分析用户年龄     |
| POST    | `/category/run`   | 开始分析商品类别     |
| POST    | `/gender/run`     | 开始分析用户性别     |
| POST    | `/province/run`   | 开始分析地址省份 |

### GET `/gender`

Returns a single object

Success: 200

```json
{
  "female": 322,
  "male": 324,
  "unknown": 354
}
```

### POST `/gender/run`

Run MapReduce Job

Success: 201

### GET `/age`

Returns a list of objects

Success: 200

```json
[
  {
    "gender": 0,
    "ageRange": 0,
    "count": 4154
  },
  {
    "gender": 0,
    "ageRange": 1,
    "count": 4156
  }
]
```

### POST `/age/run`

Run MapReduce Job

Success: 201

### GET `/province`

Returns a list of objects

Success: 200

```json
[
  {
    "province": "上海市",
    "count": 28
  },
  {
    "province": "云南",
    "count": 38
  }
]
```

### POST `/province/run`

Run MapReduce Job

Success: 201

### GET `/action`

Returns a single object

Success: 200

```json
{
  "click": 878,
  "add": 0,
  "purchase": 81,
  "subscribe": 41
}
```

### POST `/action/run`

Run MapReduce Job

Success: 201

### GET `/category`

Returns a list of objects

Success: 200

```json
[
  {
    "category": 1,
    "count": 1
  },
  {
    "category": 1005,
    "count": 5
  }
]
```

### POST `/category/run`

Run MapReduce Job

Success: 201

### GET `/prediction`

预测是否是回头客

条件放置于 Query Parameter 中，可能的值：`user_id` `age_range` `gender` `merchant_id` ，可以任意组合

200 预测成功：

```json5
{
  "prediction": false // 是否可能是回头客
}
```

404 无法预测

```json5
"no prediction"
```

样例：

例如 `/prediction?user_id=363393&merchant_id=219` ，表示用户 363393 会不会是商家 219 的回头客。你可以把它简单地当做是几个条件的组合，即 1. 用户 363393, 2. 商家 219 这两个条件凑一起。当然你也能加入其他条件，例如加入 `gender=0` ，则进一步限制只预测女性。
