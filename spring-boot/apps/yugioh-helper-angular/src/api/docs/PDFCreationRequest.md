
# PDFCreationRequest


## Properties

Name | Type
------------ | -------------
`marginLeft` | number
`marginTop` | number
`horizontalSpacing` | number
`verticalSpacing` | number
`cardWidthMM` | number
`cardHeightMM` | number
`images` | Array&lt;Blob&gt;

## Example

```typescript
import type { PDFCreationRequest } from ''

// TODO: Update the object below with actual values
const example = {
  "marginLeft": null,
  "marginTop": null,
  "horizontalSpacing": null,
  "verticalSpacing": null,
  "cardWidthMM": null,
  "cardHeightMM": null,
  "images": null,
} satisfies PDFCreationRequest

console.log(example)

// Convert the instance to a JSON string
const exampleJSON: string = JSON.stringify(example)
console.log(exampleJSON)

// Parse the JSON string back to an object
const exampleParsed = JSON.parse(exampleJSON) as PDFCreationRequest
console.log(exampleParsed)
```

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


