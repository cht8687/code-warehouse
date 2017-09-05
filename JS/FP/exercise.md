```js
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

const result = compose(
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
  )
)(dataInput)
```





