# Structural Design Patterns — Complete Study Guide
*(Based on CSE 213: Software Engineering — Nafis Tahmid, Lecturer, CSE, BUET)*

> **Structural patterns explain how to assemble objects and classes into larger structures, while keeping this structure flexible and efficient.**

This deck covers **four** structural patterns in detail, each built from a real problem → bad solution → good solution narrative:

| # | Pattern | Core Example Used |
|---|---------|--------------------|
| 1 | **Decorator** | Starbuzz Coffee (Beverage + condiments) |
| 2 | **Composite** | Pancake House & Diner Menu (tree of menu items) |
| 3 | **Adapter** | Duck Simulator (incorporating a Turkey) |
| 4 | **Bridge** | Colored Shapes / Cross-platform GUI |

A quick note on scope: the full GoF structural family has **7** patterns (Adapter, Bridge, Composite, Decorator, Facade, Flyweight, Proxy). This lecture covers the first four; a short bonus summary of the remaining three is included at the end in case your exam syllabus references them too.

---

## 🧭 Quick Decision Guide (read this first for the exam)

When you read a problem statement, look for these **trigger phrases**:

| If the problem says / implies... | Use this pattern |
|---|---|
| "Add responsibilities/features to an object **at runtime**, in any combination, without touching the base class" | **Decorator** |
| "Subclass explosion" from combining optional add-ons (e.g., toppings, discounts, extras) | **Decorator** |
| "Treat a **group** of objects and a **single** object the same way" / tree structure / part-whole hierarchy / folders-and-files / menus-and-submenus | **Composite** |
| "Make two **incompatible interfaces** work together" / integrate a legacy or third-party class without modifying it | **Adapter** |
| "A class explodes into a **matrix/grid of combinations**" (e.g., Shape × Color, Device × Remote, OS × App) because it grows along **two independent dimensions** | **Bridge** |

Rule of thumb distinction:
- **Adapter** — makes things **work together** that weren't designed to (fixes an *incompatibility*, usually applied to *existing* code).
- **Bridge** — designed **up front** to let two hierarchies **vary independently** (prevents a *future* explosion, usually applied *before* the mess is built).
- **Decorator** — adds **behavior/responsibility**, keeps the *same* interface, stackable at runtime.
- **Composite** — adds **structure** (tree of parts and wholes), treats leaf and container **uniformly**.

---

## 1. Decorator Pattern

### 📖 Definition
> **Decorator lets you attach new behaviors to objects by placing these objects inside special wrapper objects that contain the behaviors.**

### The Problem (Starbuzz Coffee)
- Starbuzz starts with a simple menu of beverages (DarkRoast, Espresso, HouseBlend, Decaf).
- Now they want to add **condiments**: Steamed Milk, Soy, Mocha, Whipped Milk.
- Clients may or may not take condiments, and can take **multiple** of the same condiment (e.g., double Mocha).

**Bad Solution 1 — Subclassing every combination**
Create a subclass for every beverage + condiment combination (`DarkRoastWithMochaAndWhip`, `EspressoWithSoy`, ...). This causes a **subclass explosion** — the number of classes grows combinatorially. Definitely not an option.

**Bad Solution 2 — Boolean flags in the superclass**
Put `hasMilk`, `hasSoy`, `hasMocha`, `hasWhip` booleans in the `Beverage` superclass and compute `cost()` there.

Problems with this design:
- New condiments **break the superclass** (you must edit it every time a new one is added → violates Open/Closed Principle).
- Some beverages (e.g., Iced Tea) may not support certain condiments but still **inherit** them anyway (bad).
- Doesn't handle **multiple quantities** of the same condiment (e.g., double mocha).

### The Idea: "Decoration"
Instead of inheritance, **start with a beverage object and "wrap" it with condiment objects at runtime.**

Example: *DarkRoast with Mocha and Whip* is built like layers of a wrapped gift:
```
Whip( Mocha( DarkRoast() ) )
```
Each wrapper (decorator) has-a reference to the object it wraps, adds its own cost/description, and then delegates to the wrapped object.

### Structure

```
        Beverage (abstract)
        + cost(): double
        + getDescription(): String
              ▲
     ┌────────┴─────────────┐
     │                       │
ConcreteBeverage      CondimentDecorator (abstract, IS-A Beverage)
(DarkRoast, Espresso,        + cost(): double            ▲
 HouseBlend, Decaf)          # beverage: Beverage (HAS-A) │
                     ┌────────────────┬────────────────┐
                  Mocha             Whip              Soy ...
```

- **Component** (`Beverage`): common interface for both wrapped and wrapping objects.
- **Concrete Component** (`DarkRoast`, `Espresso`...): the "core" object being decorated.
- **Base Decorator** (`CondimentDecorator`): implements `Beverage` AND holds a reference to a `Beverage` — this dual role (IS-A + HAS-A) is the heart of the pattern.
- **Concrete Decorators** (`Mocha`, `Whip`, `Soy`, `Milk`): each adds its own price/description, then calls the wrapped object's method.

### Code Example (Java-style)

```java
// Component
abstract class Beverage {
    String description = "Unknown Beverage";
    public String getDescription() { return description; }
    public abstract double cost();
}

// Concrete Component
class DarkRoast extends Beverage {
    public DarkRoast() { description = "Dark Roast Coffee"; }
    public double cost() { return 0.99; }
}

// Base Decorator
abstract class CondimentDecorator extends Beverage {
    protected Beverage beverage;   // HAS-A the wrapped object
    public abstract String getDescription();
}

// Concrete Decorator
class Mocha extends CondimentDecorator {
    public Mocha(Beverage beverage) { this.beverage = beverage; }
    public String getDescription() {
        return beverage.getDescription() + ", Mocha";
    }
    public double cost() {
        return beverage.cost() + 0.20;
    }
}

class Whip extends CondimentDecorator {
    public Whip(Beverage beverage) { this.beverage = beverage; }
    public String getDescription() {
        return beverage.getDescription() + ", Whip";
    }
    public double cost() {
        return beverage.cost() + 0.10;
    }
}
```

**Test Drive (building DarkRoast + double Mocha + Whip):**
```java
Beverage beverage = new DarkRoast();
beverage = new Mocha(beverage);   // DarkRoast + Mocha
beverage = new Mocha(beverage);   // + another Mocha (double mocha!)
beverage = new Whip(beverage);    // + Whip
System.out.println(beverage.getDescription() + " $" + beverage.cost());
```
Notice: each wrapper simply calls the inner object first, then adds its own bit — this is why decorators can be **stacked in any order and any quantity**, solving all three problems the boolean design had.

### When to Use
- You need to add responsibilities to individual objects **dynamically and transparently**, without affecting other objects of the same class.
- Extension by subclassing is impractical (too many independent combinations) or impossible.
- You want to be able to **add/remove** responsibilities at runtime, not just compile time.

### Pros & Cons
| ✅ Pros | ❌ Cons |
|---|---|
| Extend behavior without new subclasses (Open/Closed Principle) | Many small objects/classes can clutter the design |
| Add/remove responsibilities at runtime | Order of decorators can matter and can be confusing |
| Combine behaviors flexibly (mix & match) | Removing a specific wrapper from the middle of a stack is awkward |

### Common Exam Giveaways
"Toppings on a pizza", "add-ons on an insurance policy", "Java I/O streams (`BufferedReader(new FileReader(...))`)", "gift wrapping charges", "extra features on a subscription plan."

---

## 2. Composite Pattern

### 📖 Definition
> **Composite pattern lets you compose objects into tree structures and then work with these structures as if they were individual objects.**

### The Problem (Pancake House & Diner Menu)
- The restaurant chain has a **Pancake House Menu** and a **Diner Menu**, and wants to merge them.
- Requirement becomes "**something tree-like**": tree-structured menus with support for **submenus** and **items**.
- Traversal should be flexible — iterate over one submenu (e.g., Desserts) or the **whole hierarchy** at once.
- A `Menu` can contain `MenuItem`s **and/or** other `Menu`s (submenus) — this is a classic **part-whole** hierarchy.

### The Idea: Nodes and Leaves
Model everything — individual items (**leaves**) and containers of items (**composite nodes**) — behind **one common interface**, so client code (like a `Waitress` printing the menu) doesn't need to know or care whether it's dealing with a single `MenuItem` or an entire `Menu`.

### Structure

```
              MenuComponent (abstract "Component")
              + add(MenuComponent)
              + remove(MenuComponent)
              + getChild(int)
              + getName(), getDescription(), getPrice()
              + isVegetarian()
              + print()
                    ▲
        ┌───────────┴───────────┐
    MenuItem (Leaf)          Menu (Composite)
    + print()                + add()/remove()/getChild()
    (no children)             + print()  → loops through
                                children, calling their print()
                                (children can be MenuItem OR Menu)
```

- **Component** (`MenuComponent`): declares the interface common to both simple and complex objects; default implementations often throw `UnsupportedOperationException` for operations that don't make sense on a leaf (e.g., `add()` on a `MenuItem`).
- **Leaf** (`MenuItem`): a node with **no children** — represents an end object of a composition (e.g., "Pancakes — $2.99").
- **Composite** (`Menu`): a node that **can** have children (`MenuItem`s or other `Menu`s); implements child-related operations and typically implements operations like `print()` by **delegating/recursing** into each child.

### Code Example (Java-style)

```java
abstract class MenuComponent {
    public void add(MenuComponent m) { throw new UnsupportedOperationException(); }
    public void remove(MenuComponent m) { throw new UnsupportedOperationException(); }
    public MenuComponent getChild(int i) { throw new UnsupportedOperationException(); }

    public String getName() { throw new UnsupportedOperationException(); }
    public double getPrice() { throw new UnsupportedOperationException(); }
    public void print() { throw new UnsupportedOperationException(); }
}

// Leaf
class MenuItem extends MenuComponent {
    String name; double price;
    MenuItem(String name, double price) { this.name = name; this.price = price; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public void print() { System.out.println(" - " + name + " : $" + price); }
}

// Composite
class Menu extends MenuComponent {
    List<MenuComponent> children = new ArrayList<>();
    String name;
    Menu(String name) { this.name = name; }

    public void add(MenuComponent m) { children.add(m); }
    public void remove(MenuComponent m) { children.remove(m); }
    public MenuComponent getChild(int i) { return children.get(i); }
    public String getName() { return name; }

    public void print() {
        System.out.println("\n" + name);
        for (MenuComponent child : children) {
            child.print();   // works for BOTH MenuItem and nested Menu — recursion!
        }
    }
}
```

**Usage:**
```java
Menu dinerMenu = new Menu("Diner Menu");
dinerMenu.add(new MenuItem("Pancakes", 2.99));

Menu dessertMenu = new Menu("Desserts");
dessertMenu.add(new MenuItem("Apple Pie", 1.59));

dinerMenu.add(dessertMenu);   // a Menu inside a Menu — the tree grows
dinerMenu.print();            // one call prints the ENTIRE hierarchy
```

### When to Use
- Your data naturally forms a **tree/hierarchy** (file systems, org charts, UI widget trees, menus).
- Clients should be able to **ignore the difference** between individual objects and compositions of objects.
- You want operations to work **recursively** over a structure with a single line of client code.

### Pros & Cons
| ✅ Pros | ❌ Cons |
|---|---|
| Client code is simple — treats leaves and composites uniformly | Can make the design **overly general** — hard to restrict what types of children a container may have |
| Easy to add new leaf/composite types | Some operations don't make sense for leaves (e.g., `add()`) — usually handled via exceptions or interface segregation |
| Recursion handles arbitrarily deep trees with almost no extra code | |

### Common Exam Giveaways
"Folders containing files and other folders," "an org chart with employees and managers," "a UI containing panels and other UI elements," "a bill of materials (parts made of sub-parts)."

---

## 3. Adapter Pattern

### 📖 Definition
> **Convert the interface of a class into another interface clients expect. Adapter lets classes work together that couldn't otherwise because of incompatible interfaces.**

### The Problem (Duck Simulator + Turkey)
- Existing code is built entirely around a `Duck` interface (methods like `quack()`, `fly()`).
- A vendor gives you a `Turkey` class with a **different, incompatible interface** (`gobble()`, `fly()` — but a turkey's fly is short and choppy, not like a duck's).
- You need to make the Turkey usable **wherever Duck code is expected**, without rewriting either the existing Duck code or the vendor's Turkey code.

### Real-Life Analogy
A **power plug adapter**: your device (client) expects a certain plug shape (target interface); the wall socket (adaptee) provides a different shape. The adapter sits in between and translates one into the other — nobody changes the device or the wall.

### Two Flavors

#### A) Object Adapter (uses composition — works in every OOP language)
```
Client → Target (Duck)          Adaptee (Turkey)
              ▲                       ▲
              │                       │ (wrapped, HAS-A)
        TurkeyAdapter ───────────────┘
        implements Duck
        + quack() { turkey.gobble(); }
        + fly()   { for 5 times: turkey.fly(); }  // simulate longer flight
```
- The Adapter **implements** the Target interface, and internally **wraps (has-a)** an instance of the Adaptee, translating calls.
- Works in **all popular languages** because it relies only on composition + interface implementation, not multiple inheritance.

```java
interface Duck { void quack(); void fly(); }
interface Turkey { void gobble(); void fly(); }

class WildTurkey implements Turkey {
    public void gobble() { System.out.println("Gobble gobble"); }
    public void fly() { System.out.println("I'm flying a short distance"); }
}

// Object Adapter
class TurkeyAdapter implements Duck {
    Turkey turkey;                       // composition (HAS-A)
    public TurkeyAdapter(Turkey turkey) { this.turkey = turkey; }
    public void quack() { turkey.gobble(); }               // translate call
    public void fly() { for (int i = 0; i < 5; i++) turkey.fly(); } // adapt behavior gap
}
```
```java
Turkey turkey = new WildTurkey();
Duck turkeyAdapter = new TurkeyAdapter(turkey);
turkeyAdapter.quack();  // internally calls turkey.gobble()
turkeyAdapter.fly();    // simulated to behave like Duck.fly()
```

#### B) Class Adapter (uses inheritance)
```
class TurkeyAdapter extends Turkey implements Duck {
    public void quack() { gobble(); }   // inherited method, reused directly
    public void fly() { /* adapt */ }
}
```
- The Adapter **inherits** interfaces/implementation from **both** the Target and the Adaptee at the same time.
- Can only be implemented in languages that support **multiple inheritance** (e.g., C++). Not directly possible in Java/C# (single class inheritance), which is why Java code typically uses the Object Adapter.

### Structure Summary
- **Target**: the interface the client expects (`Duck`).
- **Adaptee**: the existing incompatible class (`Turkey`).
- **Adapter**: implements Target, and either **wraps** (object adapter) or **inherits from** (class adapter) the Adaptee, translating calls between the two interfaces.
- **Client**: uses objects only through the Target interface — it never even knows an adapter is involved.

### When to Use
- You want to use an **existing class**, but its interface doesn't match what you need.
- You want to reuse several existing subclasses that lack some common functionality that can't be added to the superclass.
- Integrating a **third-party library / legacy code** you cannot modify.

### Pros & Cons
| ✅ Pros | ❌ Cons |
|---|---|
| Lets incompatible classes work together without changing their code | Adds a layer of indirection — extra classes/complexity |
| Single Responsibility — interface/data-conversion code is separated from business logic | Sometimes simpler to just change the service class if you *can* modify it |
| Object Adapter works in any OOP language | Class Adapter needs multiple inheritance (limited language support) |

### Common Exam Giveaways
"Integrate a third-party/legacy class," "make an old API work with new client code," "XML-to-JSON translators," "a `MediaPlayer` that needs to play formats it doesn't natively support."

---

## 4. Bridge Pattern

### 📖 Definition
> **Bridge lets you split a large class or a set of closely related classes into two separate hierarchies — abstraction and implementation — which can be developed independently of each other.**

### The Problem (Colored Shapes)
- You have two shapes: `Circle` and `Square`.
- You want to add colors: `Red` and `Blue`.
- Naive approach: create one subclass **per combination** — `BlueCircle`, `RedSquare`, `BlueSquare`, `RedCircle`.
- Add a **new color** (Green) or a **new shape** (Triangle)? The class hierarchy **grows exponentially**, because shape and color vary along **two independent dimensions**.

### The Idea: Two Hierarchies
Recognize that `Shape` and `Color` are **two independent dimensions**. Instead of cramming both into one inheritance tree, switch from **inheritance to composition**:
- `Shape` **delegates** color-related work to a separate `Color` object it holds a reference to.
- Now shapes and colors can each grow their own hierarchy **independently** — `N` shapes + `M` colors needs only `N + M` classes instead of `N × M`.

### Academic Terms (GoF)
- **Abstraction**: the high-level control layer for some entity (also called the *interface*). It does no real work itself — it **delegates** to the implementation layer.
- **Implementation**: the low-level "platform" layer that the abstraction delegates to.

**Real Application — Cross-platform GUI:**
- **Abstraction**: the GUI seen by regular customers/admins.
- **Implementation**: platform-specific APIs for Linux, Windows, macOS, etc.
- The Abstraction object controls the *appearance* of the app and delegates the *actual work* to the linked Implementation object.
- Implementations are **interchangeable** — this lets the **same GUI** work across different operating systems.

### Structure

```
   Abstraction                     Implementor (interface)
   - impl: Implementor  (bridge, HAS-A)    ▲
   + operation() { impl.operationImpl(); } │
        ▲                    ┌─────────────┴──────────────┐
        │                ConcreteImplementorA      ConcreteImplementorB
  RefinedAbstraction      (e.g., Red)                (e.g., Blue)
  (e.g., Circle, Square)
```

```java
// Implementor
interface Color {
    String fill();
}
class Red implements Color { public String fill() { return "red"; } }
class Blue implements Color { public String fill() { return "blue"; } }

// Abstraction
abstract class Shape {
    protected Color color;                 // the "bridge" — HAS-A implementor
    Shape(Color color) { this.color = color; }
    public abstract void draw();
}

// Refined Abstractions
class Circle extends Shape {
    Circle(Color color) { super(color); }
    public void draw() { System.out.println("Drawing a " + color.fill() + " circle"); }
}
class Square extends Shape {
    Square(Color color) { super(color); }
    public void draw() { System.out.println("Drawing a " + color.fill() + " square"); }
}
```
```java
Shape blueCircle = new Circle(new Blue());
Shape redSquare = new Square(new Red());
blueCircle.draw();  // Drawing a blue circle
redSquare.draw();   // Drawing a red square
// Adding Green or Triangle now needs only ONE new class, not four.
```

### When to Use
- A class comes in **several variants/flavors** along **more than one independent dimension** (e.g., shape × color, device × remote-control, message × sending-method).
- You want to avoid a **permanent binding** between an abstraction and its implementation — allow both to be extended/swapped independently, even at runtime.
- You want to hide implementation details from clients completely.

### Pros & Cons
| ✅ Pros | ❌ Cons |
|---|---|
| Avoids exponential class growth (Cartesian-product explosion) | Increases design complexity by introducing an extra abstraction layer |
| Abstraction and Implementation can evolve/be extended independently | Best applied when you **anticipate** the two-dimension growth — harder to retrofit onto a highly cohesive existing class |
| Follows Open/Closed and Single Responsibility principles | |

### Bridge vs. Adapter (classic exam trap!)
| | **Adapter** | **Bridge** |
|---|---|---|
| Applied | **After** the fact, to make **existing/incompatible** classes work together | **Before/during design**, planned upfront to prevent class explosion |
| Purpose | Fixes an interface mismatch | Lets two hierarchies vary independently |
| Structure | Usually connects one interface to one unrelated class | Deliberately splits **one** concept into abstraction + implementation |

### Common Exam Giveaways
"Same operation, but must run differently per OS/device/driver," "a remote control that must work with many device brands," "notification system across email/SMS/push with multiple message types," any phrase implying **"two independent axes of variation."**

---

## Side-by-Side Comparison

| Pattern | Purpose in one line | Relationship Type | Typical Trigger Word |
|---|---|---|---|
| **Decorator** | Add responsibilities dynamically, stackable | Wraps (HAS-A + IS-A same interface) | "add-ons", "wrap", "runtime extension" |
| **Composite** | Treat individual & group objects uniformly | Tree (part-whole) | "hierarchy", "tree", "nested", "contains" |
| **Adapter** | Make incompatible interfaces cooperate | Wraps (translates one interface to another) | "incompatible", "integrate legacy/3rd-party" |
| **Bridge** | Decouple abstraction from implementation | Delegates (two independent hierarchies) | "two dimensions", "combinatorial explosion", "platform-independent" |

---

## Bonus: The Other 3 GoF Structural Patterns (not in this deck)

Your syllabus may still expect awareness of these — quick summaries:

- **Facade** — Provides a simplified, unified interface to a complex subsystem of classes, so client code only talks to one simple "front desk" object instead of many complicated ones. *Trigger: "simplify a complex library/subsystem for the client."*
- **Flyweight** — Lets you fit more objects into available memory by **sharing common parts of state** between multiple objects instead of storing everything in each object. *Trigger: "millions of similar small objects", "memory optimization", "shared intrinsic state" (e.g., character glyphs in a text editor, trees in a forest simulation).*
- **Proxy** — Provides a substitute/placeholder object that controls access to another object (for lazy loading, access control, logging, caching, or remote access), without the client knowing the difference. *Trigger: "control/restrict/delay access to an object", "virtual/protection/remote proxy."*

If your lab exam covers these too, it's worth studying them from the same source material referenced at the end of the deck (see Acknowledgement below).

---

## 📌 Exam Strategy Checklist

When you see a problem statement in the lab exam:

1. **Count the dimensions of variation.** One dimension that keeps combining optionally (extras/toppings) → **Decorator**. Two independent dimensions multiplying combinations → **Bridge**.
2. **Look for "tree" or "part-whole" language.** Nested containers of similar things → **Composite**.
3. **Look for "doesn't fit" / "incompatible" / "can't modify existing code."** → **Adapter**.
4. **Ask: is this fixing an existing mismatch, or preventing a future explosion?** Existing mismatch → Adapter. Anticipated future growth → Bridge.
5. **Draw the class diagram first** (Component/Concrete/Decorator, or Target/Adaptee/Adapter, or Abstraction/Implementor, or Component/Leaf/Composite) — matching the correct **box names** is often worth marks by itself.
6. **Name the roles explicitly** in your answer (e.g., "Here, `Beverage` is the Component, `Mocha` is a Concrete Decorator...") — examiners look for this vocabulary.

---

## Acknowledgement (as cited in the source deck)
- *Dive Into Design Patterns* — Alexander Shvets
- *Head First Design Patterns*, 2nd Edition — Eric Freeman, Elisabeth Robson
