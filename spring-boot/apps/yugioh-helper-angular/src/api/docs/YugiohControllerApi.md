# YugiohControllerApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createPDF**](YugiohControllerApi.md#createpdf) | **POST** /api/create-pdf |  |
| [**getImage**](YugiohControllerApi.md#getimage) | **GET** /api/image/small/{cardId} |  |



## createPDF

> string createPDF(request)



### Example

```ts
import {
  Configuration,
  YugiohControllerApi,
} from '';
import type { CreatePDFRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new YugiohControllerApi();

  const body = {
    // PDFCreationRequest
    request: ...,
  } satisfies CreatePDFRequest;

  try {
    const data = await api.createPDF(body);
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **request** | [](.md) |  | [Defaults to `undefined`] |

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: `*/*`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## getImage

> string getImage(cardId)



### Example

```ts
import {
  Configuration,
  YugiohControllerApi,
} from '';
import type { GetImageRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new YugiohControllerApi();

  const body = {
    // number
    cardId: 789,
  } satisfies GetImageRequest;

  try {
    const data = await api.getImage(body);
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **cardId** | `number` |  | [Defaults to `undefined`] |

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: `*/*`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)

