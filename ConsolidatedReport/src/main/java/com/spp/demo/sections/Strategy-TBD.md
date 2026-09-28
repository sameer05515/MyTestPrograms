# Ways to modify your `buildFromNode` method to accept an optional strategy parameter

> if it would be possible to pass a second optional parameter in a java method, surely I would have tried to put some
> strategy parameter in my buildFromNode method, where I might have mentioned a default strategy, if not passed from
> user. :)
> Please suggest.


Java does not have native support for optional parameters like some other languages, but there are several ways to
achieve similar behavior, including using method overloading or a `default` parameter object.

Here’s how you can modify your `buildFromNode` method to accept an optional strategy parameter:

---

### Using Method Overloading

You can define multiple versions of the `buildFromNode` method, where one version accepts a strategy and the other
doesn't.

```java
public static NodeListItemVO buildFromNode(Node node) {
    return buildFromNode(node, Strategy.DEFAULT); // Use default strategy
}

public static NodeListItemVO buildFromNode(Node node, Strategy strategy) {
    if (node == null) {
        return strategy == Strategy.DEFAULT ? builder().build() : null; // Apply strategy
    }
    return builder()
            .type(node.getType())
            .name(node.getName())
            .uniqueId(node.getUniqueId())
            .parentId(node.getParentId())
            .build();
}

// Define an enum for strategy
public enum Strategy {
    DEFAULT, NULL_RETURN
}
```

---

### Example Usage

```
NodeListItemVO voWithDefaultStrategy = NodeListItemVO.buildFromNode(null);
System.out.

println(voWithDefaultStrategy);
// Output: NodeListItemVO(uniqueId=null, name=null, type=null, parentId=null)

NodeListItemVO voWithNullReturn = NodeListItemVO.buildFromNode(null, Strategy.NULL_RETURN);
System.out.

println(voWithNullReturn);
// Output: null
```

---

### Using a Functional Interface for Strategy

Instead of using an enum, you can pass a functional interface to define custom strategies.

```java
import java.util.function.Supplier;

public static NodeListItemVO buildFromNode(Node node) {
    return buildFromNode(node, () -> builder().build()); // Default strategy
}

public static NodeListItemVO buildFromNode(Node node, Supplier<NodeListItemVO> strategy) {
    if (node == null) {
        return strategy.get(); // Apply the strategy for null handling
    }
    return builder()
            .type(node.getType())
            .name(node.getName())
            .uniqueId(node.getUniqueId())
            .parentId(node.getParentId())
            .build();
}
```

---

### Example Usage with Functional Strategy

```
NodeListItemVO voDefault = NodeListItemVO.buildFromNode(null);
System.out.

println(voDefault);
// Output: NodeListItemVO(uniqueId=null, name=null, type=null, parentId=null)

NodeListItemVO voCustom = NodeListItemVO.buildFromNode(null, () -> null);
System.out.println(voCustom);
// Output: null
```

---

### Pros and Cons of Each Approach

| **Approach**        | **Pros**                                                           | **Cons**                                                                   |
|---------------------|--------------------------------------------------------------------|----------------------------------------------------------------------------|
| Method Overloading  | Simple and readable. No additional classes or interfaces required. | Hard to extend with multiple optional parameters.                          |
| Enum Strategy       | Clear definition of strategies. Good for finite, reusable options. | Less flexible if you need to customize behavior beyond predefined options. |
| Functional Strategy | Highly flexible and supports arbitrary behaviors.                  | Slightly more verbose, especially for simple cases.                        |

Choose the approach that aligns best with your project's requirements and coding style.

---

# please combine strategy with Enum and Functional approach

> Combining the **Enum-based strategy** and **Functional interface approach** gives you the flexibility of predefined
> strategies with the extensibility of functional programming. Here's how you can achieve this:

---

### Code Implementation

```java
import java.util.function.Function;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NodeListItemVO {
    private String uniqueId;
    private String name;
    private String type;
    private String parentId;

    // Enum-based strategy with a functional interface
    public enum Strategy {
        DEFAULT(node -> NodeListItemVO.builder().build()), // Default: Empty object
        NULL_RETURN(node -> null),                        // Return null
        FALLBACK_TO_DEFAULT_NAME(node ->                  // Fallback to default name
                NodeListItemVO.builder()
                        .type(node != null ? node.getType() : "DefaultType")
                        .name(node != null ? node.getName() : "DefaultName")
                        .uniqueId(node != null ? node.getUniqueId() : "DefaultId")
                        .parentId(node != null ? node.getParentId() : "DefaultParentId")
                        .build());

        private final Function<Node, NodeListItemVO> strategyFunction;

        Strategy(Function<Node, NodeListItemVO> strategyFunction) {
            this.strategyFunction = strategyFunction;
        }

        public NodeListItemVO apply(Node node) {
            return strategyFunction.apply(node);
        }
    }

    public static NodeListItemVO buildFromNode(Node node) {
        return buildFromNode(node, Strategy.DEFAULT); // Use the default strategy
    }

    public static NodeListItemVO buildFromNode(Node node, Strategy strategy) {
        return strategy.apply(node); // Apply the selected strategy
    }
}
```

---

### Explanation

1. **Enum with Functional Behavior**:
    - The `Strategy` enum holds predefined strategies, each implemented as a `Function<Node, NodeListItemVO>`.
    - Each strategy defines how to handle the `Node` input (e.g., return an empty object, return `null`, or provide a
      fallback with defaults).

2. **Default and Custom Strategies**:
    - `DEFAULT`: Creates an empty `NodeListItemVO`.
    - `NULL_RETURN`: Returns `null` if the node is `null`.
    - `FALLBACK_TO_DEFAULT_NAME`: Provides fallback values when `node` is `null`.

3. **Flexible Method**:
    - `buildFromNode(Node node, Strategy strategy)` allows selecting the strategy to apply.
    - If no strategy is provided, the default strategy (`DEFAULT`) is used.

---

### Example Usage

```java
public class Main {
    public static void main(String[] args) {
        Node validNode = new Node("unique-123", "Sample Node", "TypeA", "parent-456");
        Node nullNode;

        // Default strategy: Empty object
        NodeListItemVO defaultVO = NodeListItemVO.buildFromNode(nullNode);
        System.out.println(defaultVO);
        // Output: NodeListItemVO(uniqueId=null, name=null, type=null, parentId=null)

        // Null return strategy
        NodeListItemVO nullReturnVO = NodeListItemVO.buildFromNode(nullNode, NodeListItemVO.Strategy.NULL_RETURN);
        System.out.println(nullReturnVO);
        // Output: null

        // Fallback to default name strategy
        NodeListItemVO fallbackVO = NodeListItemVO.buildFromNode(nullNode, NodeListItemVO.Strategy.FALLBACK_TO_DEFAULT_NAME);
        System.out.println(fallbackVO);
        // Output: NodeListItemVO(uniqueId=DefaultId, name=DefaultName, type=DefaultType, parentId=DefaultParentId)

        // Valid node with fallback strategy
        NodeListItemVO validVO = NodeListItemVO.buildFromNode(validNode, NodeListItemVO.Strategy.FALLBACK_TO_DEFAULT_NAME);
        System.out.println(validVO);
        // Output: NodeListItemVO(uniqueId=unique-123, name=Sample Node, type=TypeA, parentId=parent-456)
    }
}
```

---

### Benefits of This Approach

1. **Predefined Reusability**:
    - Strategies like `DEFAULT`, `NULL_RETURN`, and `FALLBACK_TO_DEFAULT_NAME` can be reused throughout the codebase.

2. **Flexibility**:
    - Enum allows clean, readable handling of multiple predefined strategies.
    - Functional interface allows defining custom logic for special cases.

3. **Maintainability**:
    - Adding new strategies is simple—just extend the `Strategy` enum.

4. **Graceful Handling**:
    - The approach gracefully handles null inputs or edge cases, depending on the chosen strategy.

This pattern combines the clarity of enums with the power of functional programming, making it both extensible and
readable.