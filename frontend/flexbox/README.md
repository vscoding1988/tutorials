# Flexbox - Quick guide
[More in depth guide](https://css-tricks.com/snippets/css/a-guide-to-flexbox/)
## Basic alignment
We start with the basic alignment of child elements from left to right.
```html
<!--Basic HTML-->
<div class="flex-container">
  <div class="flex-child">1</div>
  <div class="flex-child">2</div>
  <div class="flex-child">3</div>
</div>
```
```css
.flex-container {
  display: flex;
}
```
The result can be seen here.
![img.png](images/flexbox_simple.png)
