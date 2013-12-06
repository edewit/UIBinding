UIBinding
=========

Android Developer Studio project that adds the ability to bind model to fields

```java
  public class MainActivity extends AeroGearActivity {  
    @Model
    private final Pojo testPojo = new Pojo();
  
    ...
    
    @Bindable
    public class Pojo {
      private String message;

```

Now because the view has a text field that has an id called message as well these are automaticly kept in sync if the
model changes so wil the field in the view and visa versa.

You can also create binding manually:

```java
  
  DataBinder.forModel(model)
    .bind(textView, "field")
    .getModel();
```
