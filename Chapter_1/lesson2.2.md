
# Lesson 2.2: Logical Operations

## Logic

Logic operators are like math operations, but for `boolean` values.

The main logic operations are defined as:

`&`, or "AND", returns true _if both inputs are true_.

`|`, or "OR", returns true _if one or both of its inputs are true_.

`!`, or "NOT", returns true _if the its input is false_. It's different because it takes one input, where as all the rest take two.

Some examples:

```java

boolean ans;

ans = false & false;  // false AND false ==> ans is false    
ans = false & true;   // false AND true  ==> ans is false  
ans = true  & false;  // true  AND false ==> ans is false   
ans = true  & true;   // true  AND true  ==> ans is true  

ans = false | false;  // false OR false ==> ans is false 
ans = false | true;   // false OR true  ==> ans is true 
ans = true  | false;  // true  OR false ==> ans is true 
ans = true  | true;   // true  OR true  ==> ans is true  

ans = !false;         // NOT false ==> ans is true    
ans = !true;          // NOT true  ==> ans is false   

```

## Problem 1 - Basic Booleans

Create three new variables, `val4`, `val5`, and `val6`. All of them should be `boolean` type. Pick any starting values you want, as long as at least 1 of them is `false`.

Remember that the declaration of variables should happen between the comment blocks indicating it's the area to declare variables.

Create some line of code that uses two of the values and the `|` operator, and produces a result which is `true`. Store that value into `result3`.

Remember that the lines of code which manipulate variables also need to be between the "problem-solution" comment blocks.

Run your code, visit the website, and ensure the output to `result3` is `true`.

## Problem 2 - Boolean Application.

Assume that `val4` represents whether the robot driver has pushed a button that's labeled "arm raise". Assume further that `val5` represents whether the driver has pushed a button that's labeled "Emergency Stop". `true` means the button is pressed, `false` means the button is released.

Your task is to calculate the value of the output boolean `raiseArmNow`. When the value is set to `true`, the arm on the robot will move upward. When the value is `false`, the arm will stop movement.

The code to _actually_ move the arm has already been written by someone else. You just need to write the code to provide the _command_ to their code to say whether the arm should go up, or stay still.

Remember both `val4` and `val5` _could_ be either `true` or `false` - their values will change based on where the operator's fingers are at. You need to ensure your answer accounts for all _permutations_ of inputs.

What do you want `raiseArmNow` to do? How should it be related to its inputs?

<details>
<summary> Hint 1 </summary>
"Emergency Stop" means "don't move at all!". If that button is pressed, motion should never occur.
</details>

<details>
<summary> Hint 2 </summary>
The solution involves using a NOT operator on one of the inputs, then using a single boolean operator to combine that result with the other input. 
</details>

<details>
<summary> Hint 3 </summary>
The "AND" operation could be thought of as a "Force False" operation. Consider the following:

```java
result = control & ( other_expr );
```

If `control` is `false`, then `result` will always be `false`, regardless of what `( other expr )` is.

However, if `control` is `true`, then `result` is just equal to whatever `( other expr )` is.

</details>


Write a line of code to calculate the value for `raiseArmNow`. Run the code, open the website, and check the results. Change initial values vor `val3` and `val4` to check different _permutations_ of inputs from the user.
