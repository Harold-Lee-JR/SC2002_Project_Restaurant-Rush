
# Stage 2: how to run it, and what changed from Stage 1

I compared `Stage1/SRC` and `Stage2/SRC` in Harold's repo file by file. I couldn't compile or run Stage 2 here because the sandbox blocked running code from the external repo. The run steps below come from reading the code and haven't been tested.

## How to run Stage 2

```bash
git clone https://github.com/Harold-Lee-JR/SC2002_Project_Restaurant-Rush.git
cd SC2002_Project_Restaurant-Rush/Stage2
mkdir -p out
javac -d out $(find SRC -name "*.java")
java -cp out Main
```

- `Main` is in the default package, so run it with `-cp out`.
- The game asks for a number each turn. Type the number of an option and press Enter.
- To replay a scripted game, use the group's input files: `java -cp out Main < victory.txt` or `< defeat.txt`.
- You need JDK 11 or newer.

## What changed

### New classes (3)

| Class | What it does |
|---|---|
| `VIPCustomer` | Loses **5** satisfaction per waiting turn instead of 8. Gets **10% off** the bill, rounded to the nearest cent. |
| `CriticCustomer` | Loses **12** satisfaction per waiting turn. Pays full price. **Always orders the most expensive dish**, which is Pasta and takes 2 prep units. |
| `CustomerFactory` | The only place that knows the concrete customer types. It uses a fixed schedule: arrival 1 Regular, **2 VIP**, 3 Regular, **4 Critic**, 5 Regular, 6 Regular. |

### Code changes in existing classes

- **`Customer`** is now a base class with overridable hooks.
  - `getSatisfactionLoss()` already existed in Stage 1.
  - `chooseMenuItem()` is overridden only by the Critic.
  - `calculateBill(MenuItem)` is new. A Regular pays full price.
  - `getTypeLabel()` is new, so messages read "VIP Customer #2" or "Critic #4".
- **`TurnController`** now calls `customerFactory.createCustomer(id)` instead of `new Customer(id)`. That is the only logic line that changed in the controllers.
- **`Order`** takes its price from `customer.calculateBill(item)` instead of `item.getPrice()`. The discount is applied once, when the order is taken.
- **Money changed from `double` to integer cents** in `MenuItem`, `Order`, `GameConfig`, `GameResult` and `Restaurant`, and in the `money()` formatters in `UserInterface` and `TurnController`. For example, Salad is `900` and the revenue target is `4500`. This avoids rounding errors on the VIP discount.

### Logic changes

- **Who arrives:** customer #2 (arrives turn 4) is the VIP, and customer #4 (arrives turn 10) is the Critic.
- **VIP #2:** usually orders Burger by the food rule, so pays $13.50 instead of $15.00. Leaves unhappy only after about 20 turns of waiting.
- **Critic #4:** would have picked Salad under the Stage 1 food rule. Now always orders Pasta. At 12 per turn, they leave after about 9 turns of waiting, so a cheap dish served first can lose you the sale.

## What did *not* change

- The turn order is the same: arrivals, display, Waiter, Chef, payment, eating, abandonment.
- The Waiter, Chef and Cashier logic, `Decision`, and the UI flow are unchanged.
- The victory rules are the same: at least 4 paid, at least $45, and average satisfaction of at least 60.
- Regular customers still use the Stage 1 food rule.

## Things to fix before submitting

- The repo only has a **`Stage-1`** tag. The brief expects `stage-2` too, so tag after the Stage 2 PRs are merged. Check whether the tag casing should match the brief (`stage-1`).
- `Customer.java` has a stray `*/` at the end of a `//` comment (`// Name shown in messages... */`). It's harmless but untidy.

Sources: [markdown syntax cheatsheet](https://github.com/adam-p/markdown-here/wiki/markdown-cheatsheet), [Harold-Lee-JR/SC2002_Project_Restaurant-Rush](https://github.com/Harold-Lee-JR/SC2002_Project_Restaurant-Rush)
