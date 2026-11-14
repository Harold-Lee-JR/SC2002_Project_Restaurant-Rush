## Changes made from Stage 2 to 3
---

**Source:** As highlighted in out `Stage3_Class_Diagram.pdf`. **Green** texts indicates the additions made to Stage 3 and **yellow** as Stage 2 elements.

## 1. Summary

Stage 3 adds **Happy Hour**, **combo meals**, **centralized final-price calculation**, and a **Host** role. Existing controllers, user interaction, customer state and order-taking logic are extended to support these features. The diagram also explicitly calls for using integer cents rather than `double` for prices, as required by section 2.2 of the assignment.

## 2. New Stage 3 Classes

| Class | Type | Stage 3 responsibility |
| --- | --- | --- |
| `HappyHour` | Entity | Tracks activation, active duration, discount, and satisfaction recovery. |
| `Host` | Control | Seats customers separately from the Waiter, before the Waiter's action. |
| `PricingCalculation` | Control | Computes the final order price using customer-specific billing and the Happy Hour discount. |
| `ComboMeal` | Entity, extends `MenuItem` | Combines two menu items, applies a combo discount, and aggregates preparation units. |

### `HappyHour`

- Fields: `startTurn`, `activated`, `DISCOUNT = 20`, `RECOVERY = 15`, `duration = 2`.
- Methods: `activate(turn)`, `isActive(turn)`, `hasBeenActivated()`, `getRemainingTurns(turn)`, `getDiscount(turn)`, `getRecovery()`.
- Activation is offered until Happy Hour has been activated. Recovery applies to customers in `WAITING`, `SEATED`, or `ORDERED` states while active; satisfaction is capped at 100.

### `Host`

- Holds a `Restaurant` reference and provides `seatCustomer(Customer, Table)`.
- Gives the player an additional seating action before the Waiter acts. A customer seated by the Host can have their order taken by the Waiter in the same turn.

### `PricingCalculation`

- Provides `calculateFinalPrice(Customer, HappyHour, int turn): int`.
- Separates final-price calculation from the Waiter's order-taking orchestration.
- The diagram assigns customer-specific billing to `Customer`/its subclasses, combo pricing to `ComboMeal`, and the final Happy Hour adjustment to `PricingCalculation`.

### `ComboMeal`

- Extends `MenuItem` and contains two `MenuItem` references: `itemOne` and `itemTwo`.
- Uses `COMBO_DISCOUNT = 10` (10%).
- Overrides `getPrice(): int` to return the combined discounted price, rounded to the nearest cent, and `getPrepUnit(): int` to return combined preparation units.
- The diagram's example is Pasta + Burger: `$33 × 0.9 = $29.70`.

## 3. Changes to Existing Classes

### `TurnController`

**New dependencies:** `host: Host` and `happyHour: HappyHour`.

**New methods:**

- `processHostAction(Decision d, int turn)`
- `processHappyHourActivation(int turn)`
- `applyHappyHourRecovery(int turn)`
- `buildHostOptions(): List<Decision>`

**Revised turn sequence (as documented in the diagram):**

1. Process customer arrivals.
2. Display game state.
3. Prompt for Happy Hour activation and apply recovery.
4. Process Host decision.
5. Process Waiter decision.
6. Process Chef decision.
7. Process payment.
8. Process eating.
9. Process abandonment.

Payment deliberately precedes eating: customers becoming `READY_TO_PAY` during eating pay on the following turn.

### `UserInterface`

**New methods:**

- `displayHappyHour(int remainingTurns, boolean activatedBefore, boolean isActive)`
- `promptHappyHourActivation(): boolean`

The diagram notes that the activation prompt is shown each turn until Happy Hour has been activated, with the response passed to `TurnController`.

### `Customer`

**New method:** `recoverSatisfaction(int points): void`.

This supports the Happy Hour recovery mechanic. The design note restricts recovery to `WAITING`, `SEATED`, and `ORDERED`, with satisfaction capped at 100.

### `Waiter`

**Updated order-taking signature:**

```java
Order takeOrder(Customer c, PricingCalculation pricing,
                HappyHour happyHour, int turn);
```

The Waiter now receives pricing and Happy Hour collaborators and the current turn when creating an order.

### `Decision` / `Decision.Type`

The existing decision mechanism is extended to represent Host seating options. The diagram continues to use the `SEAT` decision type rather than showing a new Host-specific enum constant.

### `MenuItem` and menu contents

`MenuItem` becomes the superclass of `ComboMeal`. The diagram shows a menu association that can contain `MenuItem` objects, including combo meals, so callers can use `getPrice()` and `getPrepUnit()` polymorphically.

### Price-related fields and signatures

The diagram explicitly changes monetary values from `double` to **integer cents**. Relevant declarations shown include:

- `GameConfig.victoryRevenueCents: int = 4500`
- `GameResult.revenueCents: int`
- `Order.priceCents: int`
- `MenuItem.basePriceCents: int`
- `Order(..., priceCents: int)` and `getPrice(): int`
- `Customer.calculateBill(MenuItem): int`
- `GameResult.recordPayment(int priceCents, int satisfaction)`

This is a cross-cutting representation change, not a new gameplay feature. Satisfaction thresholds such as `victorySatisfaction: double = 60` are not monetary amounts and remain `double` in the diagram.

## 4. Behavioral Changes

| Area | Stage 3 behavior |
| --- | --- |
| Seating | Host acts before Waiter; same-turn seating and order-taking are possible. |
| Happy Hour | Player can activate the feature; it supplies a 20% discount and 15-point satisfaction recovery while active, subject to the diagram's state restrictions. |
| Order pricing | Customer-specific billing, combo pricing, and Happy Hour adjustment are handled by separate collaborating classes. |
| Menu | Combo meals can be treated as menu items; their preparation units and prices reflect both component items. |
| Critic selection | The Critic chooses the most expensive listed menu item (first item wins ties). The diagram notes that the Pasta + Burger combo at $29.70 becomes its choice. |
| Turn flow | A Host decision and Happy Hour processing are added ahead of Waiter/Chef decisions. |

## 5. OOP Design Implications

- **Inheritance and polymorphism:** `ComboMeal extends MenuItem` and overrides `getPrice()` and `getPrepUnit()`. Existing customer subclass overrides continue to support customer-specific behavior.
- **Single Responsibility Principle:** `ComboMeal` calculates combo pricing; customer classes implement customer-specific billing; `PricingCalculation` handles final pricing and Happy Hour adjustment.
- **Open/Closed Principle:** New menu-item types can extend `MenuItem` without requiring callers to special-case combo meals.
- **Encapsulation:** `HappyHour` owns activation state, while `Customer.recoverSatisfaction()` provides a controlled way to modify satisfaction.
- **Separation of concerns:** `Host` handles seating, `Waiter` handles waiter actions, and `TurnController` coordinates their order of execution.