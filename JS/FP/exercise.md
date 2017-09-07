```js
// Question 1:
let dataInput = [
  {
    "value": [
      {
        "status": "completed",
        "count": 10
      },
      {
        "status": "degraded",
        "count": 2
      },
      {
        "status": "failed",
        "count": 0
      }
    ],
    "timeframe": {
      "start": "12 am",
    }
  },
  {
    "value": [
      {
        "status": "completed",
        "count": 11
      },
      {
        "status": "degraded",
        "count": 0
      },
      {
        "status": "failed",
        "count": 1
      }
    ],
    "timeframe": {
      "start": "12 am",
    }
  },
  {
    "value": [
      {
        "status": "completed",
        "count": 9
      },
      {
        "status": "degraded",
        "count": 0
      },
      {
        "status": "failed",
        "count": 3
      }
    ],
    "timeframe": {
      "start": "12 am",
    }
  }
];   

const expectedOutput = {
     series:
     [{
      "name": "completed",
      "data": [
        10,
        11,
        9,
      ]
    },
    {
      "name": "degraded",
      "data": [
        -2,
        0,
        0,
      ]
    },
    {
      "name": "failed",
      "data": [
        0,
        -1,
        -3,
      ]
    }
  ],
  "categories": [
    "10am",
    "11am",
    "12am",
  ]
}

```

``` js 
const result = 
 converge(
  merge,
  [
    compose(  
    objOf('series'),
    values,
    map(
     (obj)=> {
       return {
         'name': obj[0]['status'], 
         'data': obj[0]['status'] !== 'completed' ? map((t)=>negate(t.count))(obj) : map((t)=>t.count)(obj)
       }
     }
    ),
    groupBy(prop('status')),
    unnest,
    map(
      prop('value')
    )),
   compose(
     objOf('categories'),
     map(path(['timeframe', 'start']))
   )
  ]
)(dataInput)

```

```js
// Question 2:


const inputData = 
[
    ["Key1", "Key2_01", 26],
    ["Key1", "Key2_10", 5972],
    ["Key1", "Key2_06", 1184],
    ["Key1", "Key2_08", 1863],
    ["Key1", "OTHER[+9]", 134],
    ["reason_02", "Key2_01", 115],
    ["reason_02", "Key2_10", 3932],
    ["reason_02", "Key2_06", 7202],
    ["reason_02", "Key2_08", 1096]
]

const output = {
    "Key2_01": 141,
    "Key2_10": 9904,
    "Key2_06": 8386,
    "Key2_08": 2959,
    "OTHER[+9]": 134,
    "Key1": 9179,
    "reason_02": 12345
}

var transform = R.compose(
  R.mapObjIndexed(
                R.compose(
                    R.sum,
                    R.map(last)
                )
            ),
  R.converge(
    R.merge,
    [
      R.groupBy(R.nth(1)),
      R.groupBy(head)
    ]
 )
)(dataInput);

```
