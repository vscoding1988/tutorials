# Custom Validation of beans in spring boot

This application should demonstrate how to create custom validators for rest services.

## User Story

- As IT department, we want to provide an endpoint for product registration, so that the current
  manual process can be automated.
- As IT department, we want that, only products could be activated, which was bought by the
  customer, so that we are safe from brute force attacks.

## Concept

We will need two information from our customer in the MVP implementation, the customer id and the
product id. More information will be needed in the feature implementation, so we have to make sure,
that the consumed data is easy to expand. The endpoint will consume data in the following JSON
format.

```json
{
  "company": {
    "id": "String"
  },
  "product": {
    "id": "String"
  }
}
```

We will need a validator for
the [Product](src/main/java/com/vscoding/tutorial/validation/boundary/form/Product.java) which will
check if a customer is allowed to activate the provided id. But because this validation is costly we
will validate
the [Company](src/main/java/com/vscoding/tutorial/validation/boundary/form/Company.java) id, because
often user don't know what the company id is and are using their own id. If the request is not valid
we would expect a json response with the error in format:

```json
{
  "field": "field id (f.e 'product.id')",
  "message": "error msg (f.e 'must not be blank')"
}
```

# Pitfalls

## Custom validator

To create a custom validator
for [Company.id](src/main/java/com/vscoding/tutorial/validation/boundary/form/Company.java) we eiter
can check the null inside the validator, or rely on `@NonBlank` and accept "null" values as valid
value in the validator. Not sure which of the ways is better, I guess null checks are fine, but when
you get more restrictions it would be cleaner to rely on the annotations.

To validate [Product.id](src/main/java/com/vscoding/tutorial/validation/boundary/form/Product.java)
we need to get access to
the [Company.id](src/main/java/com/vscoding/tutorial/validation/boundary/form/Company.java) so the
validator annotation has to be added to
the [Form](src/main/java/com/vscoding/tutorial/validation/boundary/form/Form.java) and we have to
handle null values in the same way as
for [Company.id](src/main/java/com/vscoding/tutorial/validation/boundary/form/Company.java).

## Custom error response

To create a custom json response, we need to write own `@RestControllerAdvice` (
see [FormExceptionHandler](src/main/java/com/vscoding/tutorial/validation/exception/FormExceptionHandler.java)).
