
# YugiohDeck


## Properties

Name | Type
------------ | -------------
`id` | string
`name` | string
`description` | string
`mainDeck` | [Array&lt;YugiohDataCard&gt;](YugiohDataCard.md)
`sideDeck` | [Array&lt;YugiohDataCard&gt;](YugiohDataCard.md)
`extraDeck` | [Array&lt;YugiohDataCard&gt;](YugiohDataCard.md)

## Example

```typescript
import type { YugiohDeck } from ''

// TODO: Update the object below with actual values
const example = {
  "id": null,
  "name": null,
  "description": null,
  "mainDeck": null,
  "sideDeck": null,
  "extraDeck": null,
} satisfies YugiohDeck

console.log(example)

// Convert the instance to a JSON string
const exampleJSON: string = JSON.stringify(example)
console.log(exampleJSON)

// Parse the JSON string back to an object
const exampleParsed = JSON.parse(exampleJSON) as YugiohDeck
console.log(exampleParsed)
```

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


