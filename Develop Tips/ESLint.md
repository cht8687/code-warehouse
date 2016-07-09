##Rules in Plugins
```js
module.exports = {
    rules: {
        "dollar-sign": {
            create: function (context) {
                // rule implementation ...
            }
        }
    }
};

```

##Environments in Plugins

```js
module.exports = {
    environments: {
        jquery: {
            globals: {
                $: false
            }
        }
    }
};
```

##Processors in Plugins

```js
processors: {

    // assign to the file extension you want (.js, .jsx, .html, etc.)
    ".ext": {
        // takes text of the file and filename
        preprocess: function(text, filename) {
            // here, you can strip out any non-JS content
            // and split into multiple strings to lint

            return [string];  // return an array of strings to lint
        },

        // takes a Message[][] and filename
        postprocess: function(messages, filename) {
            // `messages` argument contains two-dimensional array of Message objects
            // where each top-level array item contains array of lint messages related
            // to the text that was returned in array from preprocess() method

            // you need to return a one-dimensional array of the messages you want to keep
            return [Message];
        }
    }
}
```

##Configs in Plugins

```js
configs: {
    myConfig: {
        env: ["browser"],
        rules: {
            semi: 2,
            "myPlugin/my-rule": 2,
            "eslint-plugin-myPlugin/another-rule": 2
        }
    }
}
```

##Testing

```js

"use strict";

var rule = require("../../../lib/rules/custom-plugin-rule"),
    RuleTester = require("eslint").RuleTester;

var ruleTester = new RuleTester();
ruleTester.run("custom-plugin-rule", rule, {
    valid: [
        "var validVariable = true",
    ],

    invalid: [
        {
            code: "var invalidVariable = true",
            errors: [ { message: "Unexpected invalid variable." } ]
        }
    ]
});

```
