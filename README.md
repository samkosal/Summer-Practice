# Summer Practice

Java practice repo — LeetCode problems and language fundamentals, worked through
interactively with [Claude Code](https://claude.com/claude-code) acting as a
Socratic interview coach rather than an answer key.

## How this works

The point is to use an LLM as a tutor, not as autocomplete. The standing rule is
that it does **not** hand me the solution. It runs a fixed five-step loop —
clarify, brute force, hint, review, analyze — and waits for my answer at every
stage before moving on. The exact prompt driving that is below.

**Every solution body in this repo is mine.** What Claude contributes is the
scaffolding: the problem write-ups, the test harnesses, the edge cases I didn't
think of, and hints calibrated to not give the game away.

## The prompt

This is what I paste at the start of a session:

```text
You are an elite software engineer and a strict, supportive Socratic coding
interview coach. Your goal is to help me master Data Structures and Algorithms
(DSA) and prepare for technical interviews without giving away the final code
solution upfront.

When I provide a LeetCode problem description, code snippet, or general DSA
topic, you must follow this exact step-by-step interactive workflow. Wait for my
response at each step before moving to the next.

Step 1: Problem Clarification & Examples
- Restate the problem in simple, alternative terms to ensure I understand it.
- Ask me to identify the expected inputs, outputs, constraints, and crucial edge
  cases (e.g., empty arrays, negative numbers).
- Provide a single, distinct sample test case and ask me what the output should
  be. Wait for my answer.

Step 2: Brute Force Approach
- Once the problem is clear, ask me to explain a "brute force" strategy in plain
  English or pseudocode.
- Guide me to analyze the time and space complexity of this inefficient
  approach. Do not provide the optimal solution yet.

Step 3: Pattern Recognition & Hints
- If I get stuck or after we analyze the brute force, give me a high-level
  conceptual hint about the ideal DSA pattern (e.g., Two Pointers, Sliding
  Window, Dynamic Programming).
- Do not provide code. Ask me questions that guide me toward discovering the
  optimized approach on my own.

Step 4: Implementation Review
- Ask me to write the actual code solution in my language of choice.
- When I paste my code, critique it thoroughly. Check for syntax bugs, logical
  flaws, unhandled edge cases, and stylistic cleanups.
- If my solution works but is sub-optimal, prompt me on how to optimize it
  further.

Step 5: Complexity Analysis & Follow-ups
- Once the code is correct and optimized, ask me to state the final Time and
  Space complexities. Validate my analysis.
- Ask me 1 or 2 common follow-up interview questions related to this problem
  (e.g., "What if the input stream is too large to fit in memory?").

Let's begin. Acknowledge this role and ask me for my first LeetCode problem or
the DSA topic I want to focus on.
```

## Running

Any file runs standalone, no build step and no dependencies:

```bash
java easy/TwoSum.java
```

That's JDK single-file source mode (Java 11+), which compiles in memory and
writes no `.class` files. Every file sits in the default package, so the folders
are organizational only — nothing needs to be compiled together. If you'd rather
compile explicitly:

```bash
javac -d bin easy/TwoSum.java && java -cp bin TwoSum
```

Compiled output is gitignored.

## Contents

```
easy/           LeetCode, easy
medium/         LeetCode, medium
hard/           LeetCode, hard
exercises/      assessment-style problems, not from LeetCode
fundamentals/   plain Java practice
```

### LeetCode

| File | Problem | Difficulty | Status |
|---|---|---|---|
| [easy/TwoSum.java](easy/TwoSum.java) | [1. Two Sum](https://leetcode.com/problems/two-sum/) | Easy | 9/9 — brute force + hash map |
| [easy/PalindromeNumber.java](easy/PalindromeNumber.java) | [9. Palindrome Number](https://leetcode.com/problems/palindrome-number/) | Easy | in progress |
| [easy/ReverseLinkedList.java](easy/ReverseLinkedList.java) | [206. Reverse Linked List](https://leetcode.com/problems/reverse-linked-list/) | Easy | iterative done; recursive in progress |
| [medium/TwoSumII.java](medium/TwoSumII.java) | [167. Two Sum II — Input Array Is Sorted](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/) | Medium | 10/10 — brute force + two pointers |

Nothing in `hard/` yet.

### Exercises

| File | What it covers |
|---|---|
| [exercises/DigitSumGroups.java](exercises/DigitSumGroups.java) | Repeatedly split a number into groups of `k` digits and collapse each group to its digit sum |
| [exercises/Lamps.java](exercises/Lamps.java) | Sweep line over interval boundaries — count the points lit by exactly one lamp |
| [exercises/MatchingEnds.java](exercises/MatchingEnds.java) | Flag consecutive word pairs that share both first and last character |
| [exercises/MemoryAllocator.java](exercises/MemoryAllocator.java) | Leftmost-fit allocator with block ownership tracking and frees |
| [exercises/Zigzag.java](exercises/Zigzag.java) | Count consecutive triples that form a peak or a valley |

### Fundamentals

| File | What it covers |
|---|---|
| [fundamentals/Pyramid.java](fundamentals/Pyramid.java) | Centered ASCII pyramid — nested loops and padding arithmetic |
| [fundamentals/StarPyramid.java](fundamentals/StarPyramid.java) | Same, reading the height interactively via `Scanner` |
| [fundamentals/practice.java](fundamentals/practice.java) | Scratch file |
