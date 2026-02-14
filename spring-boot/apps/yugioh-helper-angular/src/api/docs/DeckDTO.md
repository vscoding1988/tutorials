
# DeckDTO


## Properties

Name | Type
------------ | -------------
`id` | string
`name` | string
`description` | string
`mainDeck` | [Array&lt;CardDTO&gt;](CardDTO.md)
`sideDeck` | [Array&lt;CardDTO&gt;](CardDTO.md)
`extraDeck` | [Array&lt;CardDTO&gt;](CardDTO.md)

## Example

```typescript
import type { DeckDTO } from ''

// TODO: Update the object below with actual values
const example = {
  "id": null,
  "name": null,
  "description": null,
  "mainDeck": null,
  "sideDeck": null,
  "extraDeck": null,
} satisfies DeckDTO

console.log(example)

// Convert the instance to a JSON string
const exampleJSON: string = JSON.stringify(example)
console.log(exampleJSON)

// Parse the JSON string back to an object
const exampleParsed = JSON.parse(exampleJSON) as DeckDTO
console.log(exampleParsed)
```

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


