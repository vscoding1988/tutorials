
# YugiohDataPrice


## Properties

Name | Type
------------ | -------------
`id` | number
`cardmarketPrice` | string
`tcgplayerPrice` | string
`ebayPrice` | string
`amazonPrice` | string
`coolstuffincPrice` | string

## Example

```typescript
import type { YugiohDataPrice } from ''

// TODO: Update the object below with actual values
const example = {
  "id": null,
  "cardmarketPrice": null,
  "tcgplayerPrice": null,
  "ebayPrice": null,
  "amazonPrice": null,
  "coolstuffincPrice": null,
} satisfies YugiohDataPrice

console.log(example)

// Convert the instance to a JSON string
const exampleJSON: string = JSON.stringify(example)
console.log(exampleJSON)

// Parse the JSON string back to an object
const exampleParsed = JSON.parse(exampleJSON) as YugiohDataPrice
console.log(exampleParsed)
```

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


