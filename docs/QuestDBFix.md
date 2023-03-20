## Contribute a Fix
We found an open [issue](https://github.com/questdb/questdb/issues/2623) about backslash escape on QuestDB's GitHub page. 

This issue contains two parts:
1. Backslash escape in the pattern string after `LIKE` operator. This is the one we tested and fixed.
   ```SQL
   SELECT '\quest' LIKE '\_uest'; 
    --true but should be false
    ```
2. `ESCAPE` clause. This is the one we will continue working on in the future.
   ```SQL
   SELECT 'quest' LIKE 'quest' ESCAPE 'Z'
   -- Unrecognized token
   ```

In the Black Box Testing section, we tested and confirmed that backslash escape in the pattern string after `LIKE` operator does not work. And we tried to fix it and created a [pull request](https://github.com/questdb/questdb/pull/3006#event-8699783406). A senior engineer at QuestDB reviewed our pull request and gave us suggestions, we then modified our codes accordingly. We underwent several iterations, making the changes in:
1. Code auto-reformat. For example, there should be one empty line between two test cases.
2. Algorithm optimization. I had used unnecessary and redundant conditionals but then optimized it, as advised by the PR reviewer.
3. Pull request title convention. I found that my pull request failed their Continuous Integration check. After digging into it, I realized that it was because my pull request title did not follow their convention.

The pull request has been approved and merged. Our codes are [part of their mainline source code](https://github.com/questdb/questdb/commit/3dcd5f46a59fe13c55a4e15fbb2046e7a82fe72a) now, and we have gained valuable open-source contribution experience through this process. Additionally, As we move forward, we plan to continue working on the `ESCAPE` clause, building on the knowledge and skills we gained from this group project.