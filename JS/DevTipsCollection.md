#Dev tips


### Cancel an ajax call:

```js
var xhr = $.ajax({
  type: "POST",
  url: url,
  success: success
});

xhr.abort();

```

### If checking

```js 
if (value) {
  
}
```

will evaluate to true if `value` is not:
*null
*undefined
*NaN
*empty string
*0
*false



