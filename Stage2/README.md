# Restaurant Rush: Stage 2

Turn-based command-line restaurant game in Java (SC2002, SCEF Grp 3). You are the manager and decide what the Waiter and Chef do each turn. Stage 2 adds VIP and Critic customers on top of Stage 1.

Repo: https://github.com/Harold-Lee-JR/SC2002_Project_Restaurant-Rush (folder `Stage2`, tag `stage-2`)

## Build and run

From `Stage2/SRC`:

```
javac Main.java
java Main
```

Reproducible runs (outputs match `victory-output.txt` and `defeat-output.txt`):

```
java Main < ../victory.txt    # Victory: 4 paid, $58.50, avg satisfaction 69.5
java Main < ../defeat.txt     # Defeat: 0 paid, $0.00
```

## Rules

18 turns, 2 tables, 1 Waiter, 1 Chef, 1 Cashier. One customer arrives at the start of turns 1, 4, 7, 10, 13 and 16. To win you need at least 4 paid customers, at least $45.00 revenue and average satisfaction of at least 60.

Each turn: arrivals, Waiter choice, Chef choice, Cashier payment and eating, then waiting customers lose satisfaction (anyone reaching 0 leaves). The Waiter acts before the Chef, so a dish finished this turn is served next turn at the earliest. Only valid options are shown to the player.

## Customer types

| Type | Satisfaction lost per waiting turn | Bill | Food choice |
|---|---|---|---|
| Regular | 8 | Full price | Cycles Salad, Burger, Pasta by customer number |
| VIP | 5 | 10% off | Same as Regular |
| Critic | 12 | Full price | Always the most expensive item (Pasta) |

Arrivals: #1 Regular, #2 VIP, #3 Regular, #4 Critic, #5 Regular, #6 Regular.

The Critic is the special case. Pasta takes 2 prep units and the Critic loses 12 satisfaction per turn, so the manager has to decide whether to rush them or serve quicker dishes first.

## How the code works

- `boundary/UserInterface`: all input and output, no game rules.
- `control/`: `GameController` runs the 18 turns, `TurnController` runs one turn, `Waiter`, `Chef` and `Cashier` do the tasks, `CustomerFactory` creates each arriving customer.
- `entity/`: `Customer`, `VIPCustomer`, `CriticCustomer`, `Order`, `MenuItem`, `Table`, `WaitingQueue`, `Restaurant`, `Decision`, `GameConfig`, `GameResult` and the status enums.

`Customer` has three overridable methods: `getSatisfactionLoss()`, `calculateBill()` and `chooseMenuItem()`. VIP and Critic override only what differs. `TurnController` just calls these on a `Customer`, so there are no type checks. Only `CustomerFactory` knows the concrete types. `Order` locks its price at creation using `customer.calculateBill(item)`.

## Changes from Stage 1

New files:
- `VIPCustomer.java`
- `CriticCustomer.java`
- `CustomerFactory.java`

Changed files:
- `Customer.java`: the fixed Regular behaviour became overridable methods so subclasses can change it.
- `Order.java`: price now comes from `customer.calculateBill(item)` so the VIP discount applies.
- `TurnController.java`: uses `CustomerFactory` instead of `new Customer(...)`.
- `MenuItem`, `Restaurant`, `GameConfig`, `GameResult`, `UserInterface`: money changed from `double` to integer cents, because the 10% discount gives fractional amounts.

The other files (`Main`, `GameController`, `Waiter`, `Cashier`, `Chef`, `Decision`, `Table`, `WaitingQueue`, enums) only have comment or formatting changes. No files were deleted. The turn rules were not rewritten.

Compare link (after tags are pushed): https://github.com/Harold-Lee-JR/SC2002_Project_Restaurant-Rush/compare/stage-1...stage-2

Class diagram: `Stage2/Stage2_Class_Diagram.pdf`
