# Sealed, non-sealed, final Classes (interfaces)

Sealed classes provides a more refined way to control inheritance. Before the sealed class there
were only one way to restrict the extension of a class, is by using the final modifier:

```java
public final class FinalClass {

}
```

If you tried to extend it, you will get an error, but the downside was, that you also was not able
to extend this class. With sealed classes this can be controlled in a better way, you can now make a
class final, and define classes which can extend it.

```java
public sealed class SealedClass permits SealedChild, FinalChild, NonSealedChild {

}
```

The modifier `sealed` will make the class not extendable for all classes, except for the classes
defined after `permits`. You can't create sealed classes with no classes extending it, and the child
classes has to have one the modifiers `final`,`sealed` or `non-sealed`.
