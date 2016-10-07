## Nested Promises

Wrong:

```bash
loadSomething().then(function(something) {
    loadAnotherthing().then(function(another) {
                    DoSomethingOnThem(something, another);
    });
});
```

better:

```bash
q.all([loadSomething(), loadAnotherThing()])
    .spread(function(something, another) {
        DoSomethingOnThem(something, another);
});

```


## The Broken Chain

wrong:

```bash
function anAsyncCall() {
    var promise = doSomethingAsync();
    promise.then(function() {
        somethingComplicated();
    });
    
    return promise;
}
```


better:

```bash
function anAsyncCall() {
    var promise = doSomethingAsync();
    return promise.then(function() {
        somethingComplicated()
    });   
}

```

## The Collection Kerfuffle

wrong:

```bash
function workMyCollection(arr) {
    var resultArr = [];
    function _recursive(idx) {
        if (idx >= resultArr.length) return resultArr;
            
        return doSomethingAsync(arr[idx]).then(function(res) {
            resultArr.push(res);
            return _recursive(idx + 1);
        });
    }

    return _recursive(0);
}
```

```bash
function workMyCollection(arr) {
    return q.all(arr.map(function(item) {
        return doSomethingAsync(item);
    }));    
}
```

