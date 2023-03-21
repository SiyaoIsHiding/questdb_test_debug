
<div align="center">
  <a href="https://questdb.io/" target="blank"><img alt="QuestDB Logo" src="https://questdb.io/img/questdb-logo-themed.svg" width="305px"/></a>
</div>
<p>&nbsp;</p>

# SWE 261P Project: Testing and Debugging of QuestDB
### **Team Member: Jane He, Fengnan Sun, Ming-Hua Tsai**
### **GitHub username: [SiyaoIsHiding](https://github.com/SiyaoIsHiding), [SoniaSun810](https://github.com/SoniaSun810), [alimhtsai](https://github.com/alimhtsai)**

## Table of Contents
+ [Acknowledgement](#acknowledgement)
+ [Contribute a Fix](#contribute-a-fix)
  <br/><br/>
+ [Part 1 Introduction/ Set Up/ Functional Testing and Partitioning of QuestDB](#part-1-introduction-set-up-functional-testing-and-partitioning-of-questdb)
  + [1. QuestDB Overview](#1-questdb-overview)
    + [1.1 Brief Introduction of QuestDB](#11-brief-introduction-of-questdb)
    + [1.2 Important Concepts of QuestDB](#12-important-concepts-of-questdb)
  + [2. Build and run QuestDB via Maven](#2-build-and-run-questdb-via-maven)
  + [3. Existing Test Cases](#3-existing-test-cases)
      + [3.1 Functional Testing](#31-functional-testing)
      + [3.2 Combinatorial Testing](#32-combinatorial-testing)
      + [3.3 Quick way to run test cases](#32-quick-way-to-run-test-cases)
  + [4. Functional Testing](#4-functional-testing)
      + [4.1 Introduction of Functional Testing and Partition Testing](#41-introduction-of-functional-testing-and-partition-testing)
      + [4.2 Implementation of Functional Testing and Partition Testing](#42-implementation-of-functional-testing-and-partition-testing)
      + [4.3 Escape in INSERT statements](#43-escape-in-insert-statements)
      + [4.4 Escape in SELECT statements](#44-escape-in-select-statements)
        <br/><br/>
+ [Part 2: Functional Testing and Finite State Machines of QuestDB](#part-2-functional-testing-and-finite-state-machines-of-questdb)
  + [1. Finite Models for Testing](#1-finite-models-for-testing-a-namefirsta)
  + [2. Implementations of Finite State Machine](#2-implementations-of-finite-state-machine-a-nameseconda)
  + [3. New JUnit Test Cases of Finite State Machine](#3-new-junit-test-cases-of-finite-state-machine-a-namethirda)
    <br/><br/>
+ [Part 3. White Box Testing and Coverage of QuestDB](#part-3-white-box-testing-and-coverage-of-questdb)
  + [1. Structural Testing](#1-structural-testing-a-namefirsta)
  + [2. Coverage of the Existing Test Suite](#2-coverage-of-the-existing-test-suite-a-nameseconda)
      + [2.1 Coverage Metrics](#21-coverage-metrics)
      + [2.2 Coverage Results](#22-coverage-results)
      + [2.3 Uncovered Test Cases](#23-uncovered-test-cases)
  + [3. New JUnit Test Cases of Structural Testing](#3-new-junit-test-cases-of-finite-state-machine-a-namethirda)
      + [3.1 New JUnit test case1](#31-new-junit-test-case1)
      + [3.2 New JUnit test case2](#32-new-junit-test-case2)
      + [3.3 New JUnit test case3](#33-new-junit-test-case3)
  + [4. Conclusion](#4-conclusion-a-namefourtha)
    <br/><br/>
+ [Part 4: Continuous Integration of QuestDB](#part-4-continuous-integration-of-questdb)
  + [1. Continuous Integration Overview](#1-continuous-integration-overview)
      + [1.1 What is Continuous Integration (CI)?](#11-what-is-continuous-integration-ci)
      + [1.2 Why do Continuous Integration (CI)?](#12-why-do-continuous-integration-ci)
      + [1.3 Common practices of Continuous Integration (CI)](#13-common-practices-of-continuous-integration-ci)
  + [2. Our Github Action 1: Maven Build and Test](#2-our-github-action-1-maven-build-and-test)
      + [2.1 Configuration](#21-configuration)
      + [2.2 Outcome](#22-outcome)
  + [3. Our Github Action 2: Markdown To PDF](#3-our-github-action-2-markdown-to-pdf)
      + [3.1 Configuration](#31-configuration)
      + [3.2 Outcome](#32-outcome)
  + [4. Existing Github Action: Danger - Validate PR Title](#4-existing-github-action-danger---validate-pr-title)
  + [5. Existing Azure Pipelines](#5-existing-azure-pipelines)
    <br/><br/>
+ [Part 5. Testable Design. Mocking of QuestDB](#part-5-testable-design-mocking-of-questdb)
  + [1. Testable Design](#1-testable-design)
      + [1.1 Aspects to Make a Testable Design](#11-aspects-to-make-a-testable-design)
      + [1.2 Goals to Make a Testable Design](#12-goals-to-make-a-testable-design)
  + [2. Mocking](#2-mocking)
      + [2.1 Definition of Mocking](#21-definition-of-mocking)
      + [2.2 The Utility of Mocking](#22-the-utility-of-mocking)
      + [2.3 Mock Testing vs Traditional Unit Testing](#23-mock-testing-vs-traditional-unit-testing)
  + [3. Documentation of Existing Code](#3-documentation-of-existing-code)
      + [3.1 A Difficult-Testing Code Example](#31-a-difficult-testing-code-example)
      + [3.2 Advice to Fix the Code and Implementation](#32-advice-to-fix-the-code-and-implementation)
  + [4. New Test Cases](#4-new-test-cases)
      + [4.1 Mocking](#41-mocking)
      + [4.2 Testable Design](#42-testable-design)
        <br/><br/>
+ [Part 6: Static Analyzers of QuestDB](#part-6-static-analyzers-of-questdb)
  + [1. Static Analyzers](#1-static-analyzers)
  + [2. Implementation of SpotBugs](#2-implementation-of-spotBugs)
      + [2.1 Introduction of SpotBugs](#21-introduction-of-spotBugs)
      + [2.2 The result of SpotBugs](#22-the-result-of-spotBugs)
  + [3. Implementation of Checkstyle](#3-implementation-of-checkstyle)
      + [3.1 Introduction of Checkstyle](#31-introduction-of-checkstyle)
      + [3.2 The result of Checkstyle](#32-the-result-of-checkstyle)
  + [4. Comparison of SpotBugs and CheckStyle](#4-comparison-of-spotBugs-and-checkStyle)
      + [4.1 Same Warning on Hidden Field `txn`](#41-same-warning-on-hidden-field-`txn`)
      + [4.2 Distinct Warnings](#42-distinct-warnings)

<div style="page-break-after: always;"></div>


## Acknowledgement
The lectures were well-structured and provided us with a comprehensive understanding of various testing and debugging techniques.
The group project was also an excellent opportunity for us to apply the concepts we learned in a real-world scenario.

We would like to thank Professor James for sharing his knowledge and expertise with us.
The TAs were also very supportive, always available to answer our questions and provide feedback on our work.

Overall, this course has been a valuable learning experience,
and we are grateful for the opportunity to have taken it.

<div style="page-break-after: always;"></div>

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

<div style="page-break-after: always;"></div>


# Part 1: Introduction/ Set Up/ Functional Testing and Partitioning of QuestDB

**Table of Contents**
+ [1. QuestDB Overview](#1-questdb-overview)
    + [1.1 Brief Introduction of QuestDB](#11-brief-introduction-of-questdb)
    + [1.2 Important Concepts of QuestDB](#12-important-concepts-of-questdb)
+ [2. Build and run QuestDB via Maven](#2-build-and-run-questdb-via-maven)
+ [3. Existing Test Cases](#3-existing-test-cases)
    + [3.1 Functional Testing](#31-functional-testing)
    + [3.2 Combinatorial Testing](#32-combinatorial-testing)
    + [3.3 Quick way to run test cases](#32-quick-way-to-run-test-cases)
+ [4. Functional Testing](#4-functional-testing)
    + [4.1 Introduction of Functional Testing and Partition Testing](#41-introduction-of-functional-testing-and-partition-testing)
    + [4.2 Implementation of Functional Testing and Partition Testing](#42-implementation-of-functional-testing-and-partition-testing)
    + [4.3 Escape in INSERT statements](#43-escape-in-insert-statements)
    + [4.4 Escape in SELECT statements](#44-escape-in-select-statements)



---

# 1. QuestDB Overview

## 1.1 Brief Introduction of QuestDB

[QuestDB](https://questdb.io/docs/) is a relational column-oriented database designed for time series and event data. It uses SQL with extensions for time series to assist with real-time analytics. It has a web console that allows you to query data and visualize the results in a table or plot.

| languages | # classes | LOC |
| --- | --- | --- |
| Java | 2,707 | 457,642 |
| C | 51 | 10,159 |
| C++ | 20 | 6,513 |
| CSS | 2 | 353 |
| HTML | 5 | 205 |
| JavaScript | 9 | 104 |

![](https://i.imgur.com/cUubUMx.png)


![](https://i.imgur.com/nBAnWsi.png)

## 1.2 **Important Concepts of QuestDB**

- **[Storage model](https://questdb.io/docs/concept/storage-model/)**
    - QuestDB uses a **column-based** storage model. Data is stored in tables with each column stored in its own file and its own native format. New data is appended to the bottom of each column to allow data to be organically retrieved in the same order that it was ingested.
    - The QuestDB storage model uses memory mapped files and cross-process atomic transaction updates as a low overhead method of inter-process communication. Data committed by one process can be instantaneously read by another process, either randomly (via queries) or incrementally (as a data queue). QuestDB provides a variety of reader implementations.

<p align="center">
   <img src="https://i.imgur.com/0S5bdiI.png"  width="600" align="center">
</p>
  


- **[Designated timestamp](https://questdb.io/docs/concept/designated-timestamp/)**
    - QuestDB offers the option to elect a column as a designated timestamp. This allows you to specify which column the tables will be indexed by in order to leverage time-oriented language features and high-performance functionalities.
    - Electing a designated timestamp allows you to
        - Partition tables by time range. For more information, see the [partitions reference](https://questdb.io/docs/concept/partitions).
        - Use time series joins such as `ASOF JOIN`. For more information, see the [JOIN reference](https://questdb.io/docs/reference/sql/join).

  | Type Name | Storage bits | Nullable | Description |
      | --- | --- | --- | --- |
  | timestamp | 64 | Yes | Signed offset in microseconds from Unix Epoch. |

- **[Differences from standard SQL](https://questdb.io/docs/concept/sql-extensions/)**
    - **SELECT * FROM is optional**

        ```sql
        my_table;
        -- equivalent to:
        SELECT * FROM my_table;
        ```

    - **GROUP BY is optional**

        ```sql
        SELECT a, b, c, d, sum(e) FROM tab GROUP BY a, b, c, d;
        -- equivalent to:
        SELECT a, b, c, d, sum(e) FROM tab;
        ```

    - **Implicit HAVING**

        ```sql
        SELECT a, b, c, d, sum(e)
        FROM tab
        GROUP BY a, b, c, d
        HAVING sum(e) > 100;
        -- equivalent to:
        (SELECT a, b, c, d, sum(e) s FROM tab) WHERE s > 100;
        ```


- **[Partitions](https://questdb.io/docs/concept/partitions/)**
    - QuestDB offers the option to partition tables by intervals of time. Data for each interval is stored in separate sets of files.
    - Partitioning is only possible on tables which have a designated timestamp. • Available partition intervals are `NONE`, `YEAR`, `MONTH`, `DAY`, and `HOUR`.
    - The naming convention for partition directories is as follows:


| Table Partition | Partition format |
| --- | --- |
| HOUR | YYYY-MM-DD-HH |
| DAY | YYYY-MM-DD |
| MONTH | YYYY-MM |
| YEAR | YYYY |
- **[Symbol (a new data structure)](https://questdb.io/docs/concept/symbol/)**
    - QuestDB introduces a data type called `symbol`; a data structure used to store repetitive strings. Internally, `symbol`
      types are stored as a table of integers and their corresponding string values.
    - Main properties of `symbol`:
        - Fast conversion from `string` to `int` and vice-versa when reading or writing data.
        - By default, QuestDB caches `symbol` types in memory for improved query speed and ILP ingestion speed.
    - *Main advantages:
        - Greatly improved query performance as string operations compare and write `int` types instead of `string`.
        - Greatly improved storage efficiency as `int` maps to `string` types.

- **[Indexes](https://questdb.io/docs/concept/indexes/)**
    - An index stores the row locations for each value of the target column in order to provide faster read access.
    - *Main advantage:
        - It allows you to bypass full table scans by directly accessing the relevant rows during queries with `WHERE` conditions.
    - Index creates a table of row locations for each distinct value for the target [symbol](https://questdb.io/docs/concept/symbol). Once the index is created, inserting data into the table will update the index. Lookups on indexed values will be performed in the index table directly which will provide the memory locations of the items, thus avoiding unnecessary table scans.
    - Here is an example of a table and its index table.

        ```sql
        Table                                       Index
        |Row ID | Symbol    | Value |             | Symbol     | Row IDs       |
        | 1     | A         | 1     |             | A          | 1, 2, 4       |
        | 2     | A         | 0     |             | B          | 3             |
        | 3     | B         | 1     |             | C          | 5             |
        | 4     | A         | 1     |
        | 5     | C         | 0     |
        ```

      `INSERT INTO Table values(B, 1);` would trigger two updates: one for the Table, and one for the Index.

        ```sql
        Table                                       Index
        |Row ID | Symbol    | Value |             | Symbol     | Row IDs       |
        | 1     | A         | 1     |             | A          | 1, 2, 4       |
        | 2     | A         | 0     |             | B          | 3, 6          |
        | 3     | B         | 1     |             | C          | 5             |
        | 4     | A         | 1     |
        | 5     | C         | 0     |
        | 6     | B         | 1     |
        ```


- **[Geospatial data (geohash)](https://questdb.io/docs/concept/geohashes/)**
    - A `geohash` is a convenient way of expressing a location using a short alphanumeric string, with greater precision obtained with longer strings.
    - The basic idea is that the Earth is divided into grids of defined size, and each area is assigned a unique id called its Geohash. For a given location on Earth, we can convert latitude and longitude as [the approximate center point](https://en.wikipedia.org/wiki/Geohash#Limitations_when_used_for_deciding_proximity) of a grid represented by a geohash string.

![](https://i.imgur.com/eOftVhD.png)


| Type Name | Storage bits | Nullable | Description |
| --- | --- | --- | --- |
| geohash(<size>) | 8-64 | Yes | Geohash with precision specified as a number followed by b for bits, c for chars. See https://questdb.io/docs/concept/geohashes for details on use and storage. |

# 2. Build and run QuestDB via Maven

```bash
# make sure you have maven in the project root directory
# to build: 
mvn clean package --batch-mode --quiet -DskipTests -P build-web-console,build-binaries
# to run:
# db_root_dir as the database root directory
mkdir db_root_dir
java -jar core/target/questdb-6.7.1-SNAPSHOT.jar -d db_root_dir/
# after the above command, it will print the ip address with the port, through which you can access the web console
```

# 3. Existing Test Cases

QuestDB uses **Junit** to implement unit testing in Java, which is a Regression Testing Framework that can be easily integrated with Maven.

Existing test cases locate in `src/test/java/io/questdb`. The system has been tested very comprehensively, and there is a one-to-one correspondence between the tested files and the java source code files. The tests have covered almost every class and class method in the system, so the Code Coverage is almost 100% (Number of lines of code executed)/(Total Number of lines of code in a system component). As we learned in class, different types of tests can be found in existing test cases.

## 3.1 **Functional Testing**

Test function by providing appropriate input, and verifying if the output is against the Functional requirements.

example: `src/test/java/io/questdb/griffin/AddIndexTest.java`

```java
@Test
    public void testAddIndexToColumnWithTop() throws Exception {
        assertMemoryLeak(() -> {
            compiler.compile(
                    "create table trades as (\n" +
                            "    select \n" +
                            "        rnd_symbol('ABB', 'HBC', 'DXR') sym, \n" +
                            "        rnd_double() price, \n" +
                            "        timestamp_sequence(172800000000, 1) ts \n" +
                            "    from long_sequence(1000)\n" +
                            ") timestamp(ts) partition by DAY",
                    sqlExecutionContext
            );
            compile("alter table trades add column sym2 symbol", sqlExecutionContext);
            compile("alter table trades alter column sym2 add index", sqlExecutionContext);

            **assertSql**("trades where sym2 = 'ABB'", "sym\tprice\tts\tsym2\n");
        });
    }
```

## 3.2 **Combinatorial Testing**

### Input Parameter Testing

Use multiple combinations of the input parameters to perform testing of the software product.

example: `src/test/java/io/questdb/griffin/InsertTest.java` (test by inserting different forms of timestamp string)

```java
@Test
    public void testInsertISODateStringToDesignatedTimestampColumn() throws Exception {
        final String expected = "seq\tts\n" +
                "1\t2021-01-03T00:00:00.000000Z\n";

        assertInsertTimestamp(
                expected,
                "insert into tab values (1, '2021-01-03')",
                null,
                true
        );
    }

    @Test
    public void testInsertISOMicroStringTimestampColumn() throws Exception {
        final String expected = "seq\tts\n" +
                "1\t2021-01-03T00:00:00.000000Z\n";

        assertInsertTimestamp(
                expected,
                "insert into tab values (1, '2021-01-03T00:00:00.000000Z')",
                null,
                true
        );
    }
```

### Sequence Testing

Many software testing problems involve sequences, especially in event-driven software. Combinatorial approaches are important for testing multiple configurations, but also for testing the effects of the order in which events occur on software. This is not an example of sequence testing, but we can see events can trigger the failure of one method.

`src/test/java/io/questdb/client/LineSenderBuilderTest.java`

```java
private static final String LOCALHOST = "localhost";

    @Test
    public void testAddressDoubleSet_firstAddressThenAddress() throws Exception {
        assertMemoryLeak(() -> {
            Sender.LineSenderBuilder builder = Sender.builder().address(LOCALHOST);
            try {
                builder.address("127.0.0.1");
                fail("should not allow double host set");
            } catch (LineSenderException e) {
                TestUtils.assertContains(e.getMessage(), "already configured");
            }
        });
    }
```

## 3.3 **Quick way to run test cases**

To run tests, it is not required to have the binaries nor the web console built. There are over 4000 tests that should complete within 2-6 minutes depending on your system:

```bash
mvn clean test
```

![](https://i.imgur.com/VCDTSU4.jpg)


### Existing Test Results

Tests run: 11964, **Failures: 11**, Errors: 22, Skipped: 311

| No. | Failed existing tests (Assertion failure) |
| --- | --- |
| 1,2 | PGJobContextTest.testPreparedStatementInsertSelectNullDesignatedColumn (WITH_WAL & NO_WAL) |
| 3,4 | PGJobContextTest.testExplainPlanWithBindVariablesFailsIfAllValuesArentSet (WITH_WAL & NO_WAL) |
| 5 | PGMultiStatementMessageTest.testCreateInsertThenErrorRollsBackFirstInsertAsPartOfImplicitTransactionOnTwoTables |
| 6 | PGMultiStatementMessageTest.testBeginCreateInsertCommitThenErrorDoesntRollBackCommittedFirstInsert |
| 7 | PGMultiStatementMessageTest.testCreateInsertThenErrorRollsBackFirstInsertAsPartOfImplicitTransaction |
| 8 | PGMultiStatementMessageTest.testBeginCreateInsertCommitThenErrorDoesntRollBackCommittedFirstInsertOnTwoTables |
| 9 | PGMultiStatementMessageTest.testCreateInsertDropTableSelectFromTableInBlockThrowsErrorBecauseTableDoesntExist |
| 10 | PGMultiStatementMessageTest.testCreateInsertCommitThenErrorDoesntRollBackCommittedFirstInsertOnTwoTables |
| 11 | PGMultiStatementMessageTest.testCreateInsertCommitThenErrorDoesntRollBackCommittedFirstInsert |

All the other failures are **Unexpected Exceptions.**

<p align="center">
   <img src="https://i.imgur.com/RdoYgjT.png"  width="700" align="center">
</p>


# 4. Functional Testing

## 4.1 Introduction of Functional Testing and Partition Testing

**[(Systematic) Functional Testing](https://www.geeksforgeeks.org/software-testing-functional-testing/)** is a type of software testing ****in which the system is tested against the functional requirements and specifications. It is basically defined as a type of testing which verifies that each function of the software application works in conformance with the requirement and specification. This testing is not concerned about the source code of the application, and is also known as **[black-box testing](https://www.geeksforgeeks.org/software-engineering-black-box-testing/)**. Each functionality of the software application is tested by providing appropriate test input, expecting the output and comparing the actual output with the expected output. The systematic refers to choose valuable representatives of classes that are apt to fail often or not at all.

**[Equivalence Partition Method](https://www.geeksforgeeks.org/equivalence-partitioning-method/)** is a software testing technique or black-box testing that divides input domain into classes of data, and with the help of these classes of data, test cases can be derived. An ideal test case identifies class of error that might require many arbitrary test cases to be executed before general error is observed.

## 4.2 Implementation of Functional Testing and Partition Testing

### Steps

1. Decompose the specification into equivalence partitions
    - If the specification is large, break it into independently testable features to be considered in testing
2. Select representatives
    - Representative values (including boundary values) of each input, or
    - Representative behaviors of a model
3. Form test specifications
    - Combination of input values or model behaviors
4. Produce and execute actual tests

<p align="center">
   <img src="https://i.imgur.com/yQvX6nx.png"  width="300" align="center">
</p>


### Strategy

In the following sections, We checked whether there is string escape mechanism in QuestDB as in SQL. A string escape refers to when a query contains a name with an `‘` , `%`, `_` etc., we can still query normal sentences from a database.

First, we decompose the specification into equivalence partitions; that is, whether QuestDB follows some common statements as in SQL:

- Escape in INSERT statements
- Escape in ALTER statements
- Escape in UPDATE statements
- Escape in SELECT statements

For each statement, we can decompose it into smaller partitions:

- Escape in STRING
- Escape in LIKE/ ILIKE operator

Second, we select representations and form test specifications. We select INSERT and SELECT statement and respectively test escape in STRING and LIKE/ ILIKE operator.

## 4.3 Escape in INSERT statements

### **INSERT**

In [QuestDB, the INSERT keyword](https://questdb.io/docs/reference/sql/insert/) is used to ingests selected data into a database table.

Example Query:

```sql
INSERT INTO trades (timestamp, symbol, amount, price, side)
VALUES(
    to_timestamp('2019-10-17T00:00:00', 'yyyy-MM-ddTHH:mm:ss'),
    'AAPL',
    255,
    123.33,
    'B');
```

Returned entries:

| symbol | side | price | amount | timestamp |
| --- | --- | --- | --- | --- |
| AAPL | B | 123.33 | 255 | 2019-10-17T00:00:00.000000Z |

### **Escape in INSERT with STRING**

Imagine there is a table called `EscapeInsert` and we want to insert a string which already contains a quote `‘`. In [SQL](https://www.databasestar.com/sql-escape-single-quote/), single quotes are escaped by doubling them up.

```sql
INSERT INTO EscapeInsert(field) values('My name''s Tim. I love eating Lay''s.');
```

It should return the following sentence: “My name's Tim. I love eating Lay's.”

### **Our New Test Cases**

We test QuestDB with a new test case under the `core/` directory:

In `io.questdb.griffin.InsertTest#testInsertEscapeString`

| The string value I insert | My name''s Tim. I love eating Lay''s. |
| --- | --- |
| The expected response | My name's Tim. I love eating Lay's. |
| The actual response | My name's Tim. I love eating Lay's. |

The result shows that QuestDB handle escape in insert statements successfully.

## 4.4 Escape in SELECT statements

### **SELECT**

In [QuestDB, the SELECT keyword](https://questdb.io/docs/reference/sql/select/) allows you to specify a list of columns and expressions to be selected and evaluated from a table. When selecting all, it supports `SELECT * FROM tablename`, you can also omit most of the statement and only pass the table name `tablename`. On the other hand, When select specific columns, put the names of the columns you are interested in.

Example Query:

```sql
SELECT price, amount FROM 'trades';
```

Returned entries:

| price | amount |
| --- | --- |
| 123.33 | 255 |

### **ESCAPE in SELECT with LIKE and ILIKE operator**

In [QuestDB, the LIKE operator](https://questdb.io/docs/reference/operators/pattern-matching/#likeilike) is used to check whether a string matches a pattern (case sensitive). On the other hand, ILIKE is used for case insensitive match. The pattern can contain wildcards like `_` and `%`. `_` matches any single character, while `%` matches any sequence of zero or more characters.

Example Query:

```sql
SELECT * FROM trades
WHERE symbol LIKE '%-USD'
```

Returned entries:

| symbol | side | price | amount | timestamp |
| --- | --- | --- | --- | --- |
| ETH-USD | sell | 1348.13 | 3.22455108 | 2022-10-04T15:25:58.834362Z |
| BTC-USD | sell | 20082.08 | 0.16591219 | 2022-10-04T15:25:59.742552Z |

Now, imagine there is a table called `myTable`

| comment (string) |
| --- |
| Can you give me 30% discount? |
| May I get 30 USD off? |
| The target path is \usr |

If you want to search for `30%` in the `comment` , you want a real `%`, instead of a wild card. In [other common databases SQL](https://www.sqlservertutorial.net/sql-server-basics/sql-server-like/) including the [Oracle database](https://docs.oracle.com/cd/A97630_01/text.920/a96518/cqspcl.htm#20741), you can use a `\` to escape. That means, if you query:

```sql
SELECT comment FROM myTable WHERE comment LIKE '%30\%%'
```

It will return the first entry only, without the second entry.

With this escape functionality enabled, if you want to search for `\usr` in the third entry:

```sql
select * from myTable where comment like '%\\usr'  # should return the third entry
select * from myTable where comment like '%\_sr'   # should return nothing
```

**However, the current QuestDB does not behave like this.**

### **Our New Test Cases**

Under the `core/` directory:

In ``io.questdb.griffin.engine.functions.regex.LikeFunctionFactoryTest#```testLikeEscapePercentage`

| The string value I stored | Can you give me 30% discount? |
| --- | --- |
| The pattern I queried | %30\%% |
| The expected response | Can you give me 30% discount? |
| The actual response | empty |

In `io.questdb.griffin.engine.functions.regex.LikeFunctionFactoryTest#testLikeEscapeUnderscore`

| The string value I stored | The target path is \usr |
| --- | --- |
| The pattern I queried | %\_sr |
| The expected response | empty |
| The actual response | The target path is \usr |

The `io.questdb.griffin.engine.functions.regex.ILikeFunctionFactoryTest#testLikeEscapePercentage` and `io.questdb.griffin.engine.functions.regex.ILikeFunctionFactoryTest#testLikeEscapeUnderscore` are the corresponding case insensitive test cases.

### **Fixing the Bug**

First, we locate the bug: `core.io.questdb.griffin.engine.functions.regex.AbstractLikeStrFunctionFactory#escapeSpecialChars`

We fixed the bug, with the core logic below:

```java
for (int i = 0; i < len; i++) {
    char c = pattern.charAt(i);
    if (c == '\\' && i + 1 < len && (pattern.charAt(i+1) == '_' || pattern.charAt(i+1) == '%')){
        i += 1;
        c = pattern.charAt(i);
        sink.put(c);
    } else if (c == '_')
        sink.put('.');
    else if (c == '%')
        sink.put(".*?");
    else if ("[](){}.*+?$^|#\\".indexOf(c) != -1) {
        sink.put("\\");
        sink.put(c);
    } else
        sink.put(c);
}
```

With the modified source code, we pass all the tests that the original official source code can pass, and our new test cases.

<div style="page-break-after: always;"></div>


# Part 2: Functional Testing and Finite State Machines of QuestDB

**Table of Contents**
+ [1. Finite Models for Testing](#1-finite-models-for-testing-a-namefirsta)
+ [2. Implementations of Finite State Machine](#2-implementations-of-finite-state-machine-a-nameseconda)
+ [3. New JUnit Test Cases of Finite State Machine](#3-new-junit-test-cases-of-finite-state-machine-a-namethirda)

---

## 1. Finite Models for Testing <a name="first"></a>

[A finite-state machine (FSM) or finite-state automaton (FSA, plural: automata)](https://en.wikipedia.org/wiki/Finite-state_machine), finite automaton, or simply a state machine, is a mathematical model of computation. It is an abstract machine that can be in exactly one of a finite number of states at any given time. They are useful for testing because they allow you to isolate specific parts of a system and test them in a controlled environment without having to worry about the complexity of the overall system.

Advantages of using FSM to implement testing:

- Allow testers to identify potential issues and bugs early in the development process, before they become a major problem.
- Make it easier to perform large-scale and repetitive testing. This can also reduce the cost of testing, as it minimizes the need for manual intervention.
- Help to test the performance of a system, by modeling different load conditions and measuring how the system behaves under stress.

## 2. Implementations of Finite State Machine <a name="second"></a>

We have selected a component of the TABLE in QuestDB to construct our finite state machine, which comprises two distinct states, referred to as nodes: `Empty Table` and `Non-empty Table`. These two states represent the current state of the table and determine the actions that can be performed on it.

Transitions between these two states are executed through two primary actions, referred to as edges: `Insert data` and `Truncate table`. These two edges represent the two key actions that can be performed on a table and determine the resulting state of the table. Additionally, there is a third edge, `Update data`, which can be performed on a `Non-empty Table` state.

1. When starting a QuestDB, the first step is to create a new table. This is accomplished by executing a CREATE TABLE statement in SQL. Upon successful execution, the database transitions into the `Empty Table` state, representing that the table has been created, but no data has been inserted into it.
2. From the `Empty Table` state, we can `Insert data` into the table by executing an INSERT INTO statement in SQL. This results in a transition of the database into the `Non-empty Table` state, representing that the table now contains data.
3. Once in the `Non-empty Table` state, we have the ability to `Update data` within the table. This can be achieved by executing an UPDATE statement in SQL. Importantly, performing this action does not result in a transition of the database state, as the contents of the table have changed, but the overall state of the table remains `Non-empty Table`.
4. When we wish to clear the contents of the table, we can `Truncate table` by executing a TRUNCATE TABLE statement in SQL. This causes all data within the table to be removed, effectively making the table empty once again. Upon successful execution of the TRUNCATE TABLE statement, the database transitions back to the `Empty Table` state.

<p align="center">
   <img src="https://i.imgur.com/H7qBEV1.png"  width="500" align="center">
</p>

## 3. New JUnit Test Cases of Finite State Machine <a name="third"></a>

We have enhanced our previous finite state machine implementation by incorporating 6 JUnit test cases located in the `core/src/test/java/testingdebugging/FSMTest.java` file. By utilizing JUnit, we are able to automate the testing process and verify the proper functioning of our finite state machine. This helps to identify and fix potential bugs, and ensures the code remains reliable and consistent as changes are made.

### (1) `#testQuestDBStateMachineAtEmptyTableState`

This test case evaluates whether the state is in the `Empty Table` state.

Upon executing the CREATE TABLE statement, a new table named "tab" is created with two columns, "id" and "text", having data types of integer and string, respectively. When we query the "tab" in QuestDB, the actual response matches the expected outcome which show the header of the “tab”, and the table is empty.

| Attribute                | Content                                |
|--------------------------|----------------------------------------|
| The statement I executed | create table tab (id int, text string) |
| The pattern I queried    | tab                                    |
| The expected response    | id\ttext\n                             |
| The actual response      | id\ttext\n                             |

### (2) `#testQuestDBStateMachineAtEmptyTableStateInsert`

This test case evaluates the transition from the `Empty Table` state to the `Non-empty Table` state through the `Insert data` action.

An empty table named "tab" is used for this test. The INSERT statement is executed to add data to the table. Upon querying the "tab" in QuestDB, the actual response matches the expected outcome, showcasing the current data information.

| Attribute                | Content                                                                      |
|--------------------------|------------------------------------------------------------------------------|
| The statement I executed | create table tab (id int, text string)<br>insert into tab values (1, 'test') |
| The pattern I queried    | tab                                                                          |
| The expected response    | id\ttext\n + "1\ttest\n                                                      |
| The actual response      | id\ttext\n + "1\ttest\n                                                      |

```java
public void createTab() throws SqlException {
    compiler.compile("create table tab (id int, text string)", sqlExecutionContext);
}

@Test
public void testQuestDBStateMachineAtEmptyTableState() throws Exception {
    assertMemoryLeak(() -> {
                createTab();
                assertSql("'tab'", "id\ttext\n");
                assertQuery(
                        "count\n" + "0\n",
                        "select count() from tab",
                        null,
                        false,
                        true
                );
            }
    );
}

@Test
public void testQuestDBStateMachineAtEmptyTableStateInsert() throws Exception {
    assertMemoryLeak(
            () -> {
                createTab();
                executeInsert("insert into tab values (1, 'test')");
                assertSql("'tab'", "id\ttext\n" + "1\ttest\n");
            }
    );
}
```

### (3) `#testQuestDBStateMachineAtEmptyTableStateUpdate`

This test case assesses the transition from the `Empty Table` state to the `Empty Table` state through the `Update data` action.

An empty table named "tab" is used for this test. The UPDATE statement is executed to modify the specified data in the table. When querying the "tab" in QuestDB, the actual response aligns with the expected outcome, displaying the information of the empty table “tab”.

| Attribute                | Content                                                                                     |
|--------------------------|---------------------------------------------------------------------------------------------|
| The statement I executed | create table tab (id int, text string)<br>update tab set text = 'test2' where text = 'test' |
| The pattern I queried    | tab                                                                                         |
| The expected response    | id\ttext\n                                                                                  |
| The actual response      | id\ttext\n                                                                                  |

### (4) `#testQuestDBStateMachineAtNonEmptyTableStateUpdate`

This test case assesses the transition from the `Non-empty Table` state to the `Non-empty Table` state through the `Update data` action.

An empty table named "tab" is used for this test. Initially, data is inserted into the table through the execution of the INSERT statement. Next, the UPDATE statement is executed to modify the previously inserted data. The actual response upon querying the "tab" in QuestDB matches the expected outcome, displaying the updated information of the "tab".

| Attribute                | Content                                                                                                                           |
|--------------------------|-----------------------------------------------------------------------------------------------------------------------------------|
| The statement I executed | create table tab (id int, text string)<br>insert into tab values (1, 'test')<br>update tab set text = 'test2' where text = 'test' |
| The pattern I queried    | tab                                                                                                                               |
| The expected response    | id\ttext\n + "1\ttest2\n                                                                                                          |
| The actual response      | id\ttext\n + "1\ttest2\n                                                                                                          |

```java
public void createTab() throws SqlException {
    compiler.compile("create table tab (id int, text string)", sqlExecutionContext);
}

@Test
public void testQuestDBStateMachineAtEmptyTableStateUpdate() throws Exception {
    assertMemoryLeak(() -> {
        createTab();
        executeUpdate("update tab set text = 'test2' where text = 'test'");
        assertSql("'tab'", "id\ttext\n");
        assertQuery(
                "count\n" + "0\n",
                "select count() from tab",
                null,
                false,
                true
        );
    });
}

@Test
public void testQuestDBStateMachineAtNonEmptyTableStateUpdate() throws Exception {
    assertMemoryLeak(() -> {
        createTab();
        executeInsert("insert into tab values (1, 'test');");
        executeUpdate("update tab set text = 'test2' where text = 'test'");
        assertSql("'tab'", "id\ttext\n" + "1\ttest2\n");
    });
}
```

### (5) `#testQuestDBStateMachineAtEmptyTableStateTruncate`

This test case assesses the transition from the `Empty Table` state to the `Empty Table` state through the `Truncate Table` action.

An empty table named "tab" was used for this test. The Truncate Table tab statement was executed to delete all data. Upon querying the "tab" in QuestDB, the actual response matched the expected outcome, showcasing the table was still empty.

| Attribute                | Content                                                      |
|--------------------------|--------------------------------------------------------------|
| The statement I executed | create table tab (id int, text string)<br>truncate table tab |
| The pattern I queried    | tab                                                          |
| The expected response    | id\ttext\n                                                   |
| The actual response      | id\ttext\n                                                   |

### (6) `#testQuestDBStateMachineAtNonEmptyTableStateTruncate`

This test case assesses the transition from the `Non-empty Table` state to the `Empty Table` state through the `Truncate Table` action.

A table named "tab" was used for this test. Initially, data was inserted into the table through the execution of the INSERT statement, and table “tab” became non-empty. Next, The Truncate Table tab statement was executed to delete all data. The actual response upon querying the "tab" in QuestDB matched the expected outcome, showcasing the table is empty again.

| Attribute                | Content                                                                                            |
|--------------------------|----------------------------------------------------------------------------------------------------|
| The statement I executed | create table tab (id int, text string)<br>insert into tab values (1, 'test')<br>truncate table tab |
| The pattern I queried    | tab                                                                                                |
| The expected response    | id\ttext\n                                                                                         |
| The actual response      | id\ttext\n                                                                                         |

```java
public void createTab() throws SqlException {
    compiler.compile("create table tab (id int, text string)", sqlExecutionContext);
}

@Test
public void testQuestDBStateMachineAtEmptyTableStateTruncate() throws Exception {
    assertMemoryLeak(() -> {
        createTab();
        Assert.assertEquals(TRUNCATE, compiler.compile("truncate table tab;", sqlExecutionContext).getType());
        assertSql("'tab'", "id\ttext\n");
        assertQuery(
                "count\n" +
                        "0\n",
                "select count() from tab",
                null,
                false,
                true
        );
    });
}

@Test
public void testQuestDBStateMachineAtNonEmptyTableStateTruncate() throws Exception {
    assertMemoryLeak(() -> {
        createTab();
        executeInsert("insert into tab values (1, 'test')");
        Assert.assertEquals(TRUNCATE, compiler.compile("truncate table tab;", sqlExecutionContext).getType());
        assertSql("'tab'", "id\ttext\n");
        assertQuery(
                "count\n" +
                        "0\n",
                "select count() from tab",
                null,
                false,
                true
        );
    });
}
```

<div style="page-break-after: always;"></div>


# Part 3. White Box Testing and Coverage of QuestDB

**Table of Contents**
+ [1. Structural Testing](#1-structural-testing-a-namefirsta)
+ [2. Coverage of the Existing Test Suite](#2-coverage-of-the-existing-test-suite-a-nameseconda)
    + [2.1 Coverage Metrics](#21-coverage-metrics)
    + [2.2 Coverage Results](#22-coverage-results)
    + [2.3 Uncovered Test Cases](#23-uncovered-test-cases)
+ [3. New JUnit Test Cases of Structural Testing](#3-new-junit-test-cases-of-finite-state-machine-a-namethirda)
    + [3.1 New JUnit test case1](#31-new-junit-test-case1)
    + [3.2 New JUnit test case2](#32-new-junit-test-case2)
    + [3.3 New JUnit test case3](#33-new-junit-test-case3)
+ [4. Conclusion](#4-conclusion-a-namefourtha)

---
<p style="page-break-after:always"></p>

## 1. Structural Testing <a name="first"></a>

[Structural testing, also known as white-box testing](https://en.wikipedia.org/wiki/White-box_testing), is a software testing technique that evaluates the internal structure of the application being tested, rather than just its functionality ([black-box testing](https://en.wikipedia.org/wiki/Black-box_testing)). This involves analyzing individual components, modules, and source code to verify that the application performs as intended and that all code paths are executed correctly.

The main advantages of structural testing are:

1. Early defect detection: Structural testing helps to identify defects and errors in the software development process by scrutinizing the application's internal structure. This enables developers to pinpoint and fix potential problems before releasing the software to users.
2. Improved quality: By concentrating on the internal structure of the application, structural testing can detect performance-critical areas, allowing developers to optimize the code and enhance the system's overall performance. It can also ensure that the codebase adheres to established design and coding standards, resulting in software that is more dependable and maintainable.
3. Improved code coverage: Structural testing provides a comprehensive view of how much of the code has been executed during testing. This enables developers to identify areas of the codebase that have not been thoroughly tested, thereby enhancing test coverage and ensuring that all parts of the software have been thoroughly tested.

However, one primary disadvantage of structural testing is that it may not detect all defects or errors, as some issues may only emerge under specific conditions or in production environments. Therefore, the main objective of structural testing is to increase confidence in the thoroughness of the testing process.

In summary, structural testing is essential for ensuring the quality of the software application. By examining the application's internal structure, structural testing can reveal errors or defects that may not be detected through functional testing. This can help to enhance the overall functionality and reliability of the application, which is critical for ensuring that it meets the needs of its users.

<p style="page-break-after:always"></p>

## 2. Coverage of the Existing Test Suite <a name="second"></a>

### 2.1 Coverage Metrics

We used two coverage tools, Intellij IDEA and [JaCoCo](https://www.jacoco.org/jacoco/trunk/index.html), to measure the extent to which the source code of a program has been tested. There are four types of code coverage metrics:

1. **Line coverage**: This metric measures the percentage of code lines executed during testing. It is the most basic type of code coverage and indicates how many lines of code have been tested in comparison to the total number of lines in the source code.
2. **Branch coverage**: This metric measures the percentage of decision points (branches) that have been executed during testing. It shows how many of the possible paths through the code have been tested.
3. **Method coverage**: This metric measures the percentage of methods that have been executed during testing. It indicates the number of methods that have been executed out of the total number of methods in the code.
4. **Class coverage**: This metric measures the percentage of classes that have been executed during testing. It shows how many of the classes in the code have been tested.

### 2.2 Coverage Results

The results of Coverage Runner JaCoCo and IntelliJ IDEA are as show below. They indicate that the existing test suite has good overall coverage across the four metrics. Class, method, and line coverage are all over 90%, while branch coverage is slightly lower at over 80%.

|               | Overall Class Coverage | Overall Method Coverage | Overall Line Coverage | Overall Branch Coverage |
|---------------|------------------------|-------------------------|-----------------------|-------------------------|
| Intellij IDEA | 96% (2478/2558)        | 90% (17920/19800)       | 91% (83952/91395)     | -                       |
| JaCoCo        | 96% (2469/2551)        | 90% (17919/19807)       | 91% (90202/98193)     | 82% (65628/79334)       |

<p style="page-break-after:always"></p>

**IntelliJ IDEA Coverage**

![](https://i.imgur.com/ZUzI4iH.jpg)


<p style="page-break-after:always"></p>

**JaCoCo Coverage**

![](https://i.imgur.com/S1EN4ov.png)


<p style="page-break-after:always"></p>

### 2.3 Uncovered Test Cases

The testing of QuestDB is already quite comprehensive; however, classes and methods in components Jit, Metrics and Network have not been fully tested due to various limitations during testing. Because of these constraints, we did not write more tests for the following three components, but chose to add new test case for class `TableReaderSelectedColumnRecordCursor` in the cairo component, class `SqlKeywords` in the griffin component, and class `ConcurrentHashMap` in the std component.

- **Jit Component**
    - Current Testing Coverage: 75% (class), 84% (method), 88% (line).
    - Functionality:  JIT stands for "Just-In-Time" compilation, and it is a feature that improves the performance of query execution. JIT compilation is a technique that compiles frequently executed code at runtime, just before it is executed. This allows the code to be optimized for the specific hardware and operating system on which it is running. In the context of a database system like QuestDB, JIT can be used to improve the performance of query execution by optimizing the code generated by the database's query engine.
    - Restriction of testing: JIT may be influenced by the complexity of the queries being executed, the specific hardware and operating system on which QuestDB is running, and other factors that can affect code optimization. This can make it difficult to determine the optimal configurations and settings for JIT, and to accurately measure its impact on query performance. We cannot implement JIT compilation due to operating system incompatibility.
    - Only x86-64 CPUs are currently supported.

    ```java
    public final class JitUtil {
    
        private JitUtil() {
        }
    
        public static boolean isJitSupported() {
            // TODO what about FREEBSD_ARM64?
            return Os.type != Os.LINUX_ARM64 && Os.type != Os.OSX_ARM64;
        }
    }
    ```


- **Metrics** **Component**
    - Current Testing Coverage: 82% (class), 71% (method), 83% (line).
    - Functionality:  Metrics provide a way to monitor the performance and usage of the database system. Specifically, metrics are a set of quantitative measurements that are collected by QuestDB and can be used to monitor various aspects of the database, such as resource usage, query performance, and throughput.
    - Restriction of testing: Metrics may be influenced by external factors, such as the performance of the underlying hardware or the presence of other applications running on the same system. This can make it difficult to isolate the impact of QuestDB on the system and to accurately measure the performance of the database.

- **Netwok** **Component**
    - Current Testing Coverage: 77% (class), 65% (method), 56% (line).
    - Functionality: the Network component in QuestDB is responsible for handling incoming and outgoing connections, as well as managing the flow of data between clients and the database. This includes handling client requests, processing incoming data, and sending responses back to the client.
    - Restriction of testing: testing the Network component may require the use of multiple systems and configurations, which can be time-consuming and expensive to set up and maintain. Additionally, the reliability and stability of the network itself can impact the results of network testing, making it difficult to accurately measure the performance and scalability of QuestDB in a distributed environment.

<p style="page-break-after:always"></p>

## 3. New JUnit Test Cases of Structural Testing <a name="third"></a>

### 3.1 New JUnit test case1

A new test case is added in `src/test/java/io/questdb/cairo/TableReaderTest.java`, which increase the coverage by **42 lines of code** for Class `TableReaderSelectedColumnRecordCursor`. In this test case, we initiated a TableReaderSelectedColumnRecordCursor Instance called tableReaderCursor and test 4 methods in this class: `public void of(TableReader reader)`, `public boolean hasNext()`, `public Record getRecord()` and `public Record getRecordB()`.

|                         | Class Coverage | Method Coverage | Line Coverage | Branch Coverage |
|-------------------------|----------------|-----------------|---------------|-----------------|
| Previous Testing Result | 0% (0/1)       | 0% (0/15)       | 0% (0/60)     | 0% (0/32)       |
| New Testing Result      | 100% (1/1)     | 66% (10/15)     | 70% (42/60)   | 43% (14/32)     |

```java
/**
 * Debugging and Testing Project
 * New added test case, test methods in TableReaderSelectedColumnRecordCursor
 * @throws Exception
 */

@Test
public void testMethodsInTableReaderSelectedColumnRecordCursor() throws Exception {

    TestUtils.assertMemoryLeak(() -> {

        try (TableModel model = new TableModel(
                configuration,
                "char_test",
                PartitionBy.NONE
        ).col("cc", ColumnType.STRING)) {
            CairoTestUtils.create(model);
        }
        char[] data = {'a', 'b', 'f', 'g'};
        try (TableWriter writer = newTableWriter(configuration, "char_test", metrics)) {

            for (int i = 0, n = data.length; i < n; i++) {
                TableWriter.Row r = writer.newRow();
                r.putStr(0, data[i]);
                r.append();
            }
            writer.commit();
        }

        final IntList list = new IntList();
        for (int i = 0; i < 10; i++) {  list.add(i); }
        TableReaderSelectedColumnRecordCursor tableReaderCursor = new TableReaderSelectedColumnRecordCursor(list);

        try (TableReader reader = newTableReader(configuration, "char_test")) {
            tableReaderCursor.of(reader);
            Boolean hasNextLine = tableReaderCursor.hasNext();
            Assert.assertEquals(true, hasNextLine);

            Record recordA = tableReaderCursor.getRecord();
            Record recordB = tableReaderCursor.getRecordB();
            Assert.assertEquals(recordA.getClass(), recordB.getClass());
        }

    });
}
```

### 3.2 New JUnit test case2

We have added new test cases to the `core` directory in `src/test/java/io/questdb/griffin/SqlKeywordsTest.java`, resulting in a **7-line increase in coverage**. The original test cases did not test the opposite cases in the if-else statement, so we added 7 opposite statement in the `isByKeyword`, `isExclusiveKeyword`, `isIntersectKeyword`, `isWeekKeyword`, `isWriterKeyword`, `isYearKeyword`, and `isIntoKeyword` to address this deficiency and improve coverage. These new test cases compensated for the lack of coverage in branches.

|                         | Class Coverage | Method Coverage | Line Coverage    | Branch Coverage |
|-------------------------|----------------|-----------------|------------------|-----------------|
| Previous Testing Result | 100% (1/1)     | 100% (148/148)  | 99% (1268/1275)  | 64% (2494/3884) |
| New Testing Result      | 100% (1/1)     | 100% (148/148)  | 100% (1275/1275) | 64% (2508/3884) |

```java
@Test
public void testBy() {
    Assert.assertFalse(isByKeyword("by0"));
    Assert.assertTrue(isByKeyword("by"));
}

@Test
public void testExclusive() {
    Assert.assertFalse(isExclusiveKeyword("exclusive0"));
    Assert.assertTrue(isExclusiveKeyword("exclusive"));
}

@Test
public void testIntersect() {
    Assert.assertFalse(isIntersectKeyword("intersect0"));
    Assert.assertTrue(isIntersectKeyword("intersect"));
}

@Test
public void testWeek() {
    Assert.assertFalse(isWeekKeyword("week0"));
    Assert.assertTrue(isWeekKeyword("week"));
}

@Test
public void testWriter() {
    Assert.assertFalse(isWriterKeyword("writer0"));
    Assert.assertTrue(isWriterKeyword("writer"));
}

@Test
public void testYear() {
    Assert.assertFalse(isYearKeyword("year0"));
    Assert.assertTrue(isYearKeyword("year"));
}

@Test
public void testInto() {
    Assert.assertFalse(isIntoKeyword("into0"));
    Assert.assertTrue(isIntoKeyword("into"));
}
```

### 3.3 New JUnit test case3

We have added new test cases to the `core` directory in `src/test/java/io/questdb/std/ConcurrentHashMapTest.java`, resulting in a **34-line increase in coverage**. The original test cases did not test these methods of concurrent hash map: `containsValue()`, `equals()`, `hashCode()`, and `toString()`. Therefore, we addressed this deficiency and enhanced the coverage in methods.

|                         | Class Coverage | Method Coverage | Line Coverage    | Branch Coverage |
|-------------------------|----------------|-----------------|------------------|-----------------|
| Previous Testing Result | 78% (15/19)    | 52% (83/158)    | 43% (652/1483)   | 29% (784/2636)  |
| New Testing Result      | 78% (15/19)    | 55% (88/158)    | 46% (686/1483)   | 30% (798/2636)  |

```java
// new added to test containsValue
@Test
public void testContainsValue() {
    ConcurrentHashMap<String> map = new ConcurrentHashMap<>(4, false);
    map.put("TABLE", "5");
    assertTrue(map.containsValue("5"));
    assertFalse(map.containsValue("0"));
}

// new added to test equals()
@Test
public void testEquals() {
    ConcurrentHashMap<String> map = new ConcurrentHashMap<>(4, false);
    map.put("TABLE", "5");
    assertFalse(map.equals(identityMap()));
}

// new added to test hashCode()
@Test
public void testHashCode() {
    ConcurrentHashMap<String> map = new ConcurrentHashMap<>(4, false);
    map.put("TABLE", "5");
    assertEquals(110115835, map.hashCode());
}

// new added to test toString()
@Test
public void testToString() {
    ConcurrentHashMap<String> map = new ConcurrentHashMap<>(4, false);
    map.put("TABLE", "5");
    assertEquals("{TABLE=5}", map.toString());
}
```

<p style="page-break-after:always"></p>

## 4. Conclusion <a name="fourth"></a>

After adding three new JUnit test cases, we conducted white-box testing with JaCoCo again. All four metrics showed an increase in coverage: there was a one-class coverage increase, an 18-method coverage increase, an 83-line coverage increase, and a 40-branch coverage increase.

|               | Overall Class Coverage | Overall Method Coverage | Overall Line Coverage | Overall Branch Coverage |
|---------------|------------------------|-------------------------|-----------------------|-------------------------|
| Original      | 96% (2469/2551)        | 90% (17919/19807)       | 91% (90202/98193)     | 82% (65628/79334)       |
| After Revised | 96% (2470/2551)        | 90% (17937/19807)       | 91% (90285/98193)     | 82% (65668/79334)       |

![](https://i.imgur.com/ehecDPf.jpg)


<div style="page-break-after: always;"></div>


# Part 4: Continuous Integration of QuestDB

**Table of Contents**
+ [1. Continuous Integration Overview](#1-continuous-integration-overview)
    + [1.1 What is Continuous Integration (CI)?](#11-what-is-continuous-integration-ci)
    + [1.2 Why do Continuous Integration (CI)?](#12-why-do-continuous-integration-ci)
    + [1.3 Common practices of Continuous Integration (CI)](#13-common-practices-of-continuous-integration-ci)
+ [2. Our Github Action 1: Maven Build and Test](#2-our-github-action-1-maven-build-and-test)
    + [2.1 Configuration](#21-configuration)
    + [2.2 Outcome](#22-outcome)
+ [3. Our Github Action 2: Markdown To PDF](#3-our-github-action-2-markdown-to-pdf)
    + [3.1 Configuration](#31-configuration)
    + [3.2 Outcome](#32-outcome)
+ [4. Existing Github Action: Danger - Validate PR Title](#4-existing-github-action-danger---validate-pr-title)
+ [5. Existing Azure Pipelines](#5-existing-azure-pipelines)

----
<p style="page-break-after:always"></p>

## 1. Continuous Integration Overview

### 1.1 What is Continuous Integration (CI)?
Continuous Integration (CI) is a software development practice in which developers merge their code changes frequently into a central repository, and each merge is automatically built, tested, and verified by an automated build system.

### 1.2 Why do Continuous Integration (CI)?
The main purpose of CI is to catch and correct errors early in the development process, before they become bigger problems, by continually integrating new code changes and testing them for errors.

The benefits of implementing a Continuous Integration (CI) system are numerous. Some of the main advantages of CI are:

1. Early detection of bugs and errors: CI allows developers to catch and fix errors early in the development process, which saves time and reduces the cost of fixing errors later on.
2. Improved code quality: By integrating new code changes frequently and testing them for errors, CI helps ensure that code is of higher quality, more stable, and more reliable.
3. Faster delivery of new features: By accelerating the process of integrating and deploying code changes, it enables frequent releases of new features and, as a result, feedback.
4. Better collaboration: CI promotes better collaboration among team members by providing a shared platform for development and testing, improving communication, and reducing the risk of conflicts between team members.

### 1.3 Common practices of Continuous Integration (CI)
There are several best practices that are commonly used in Continuous Integration (CI), including:
1. Using a single source version control system: Version control systems, such as Git or SVN, is essential for implementing CI, as it allows developers to easily manage code changes and track versions.
2. Automated builds: CI systems automatically build the code and test it for errors, allowing developers to focus on writing code instead of manually building and testing it.
3. Automated testing: Automated testing ensures that the code is functioning correctly and meets the required specifications.
4. Continuous feedback: CI systems provide continuous feedback to developers on the status of their code changes, helping them identify errors and resolve issues quickly.
5. Automated deployment: CI systems can be integrated with continuous deployment (CD) systems, allowing developers to deploy code changes to production as soon as they are ready.
6. Clone the production environment in the runner: The build and test process should run on the same environment as in production to simulate the codes' behavior when deployed in production.
7. Process visibility: Enable global access to the status of the CI processes
8. Ensure access to the latest executable: As the source code in the main line is centralized, everyone should have access to the latest executable for various uses, such as testing.
9. Short run time of CI process: The processing time of running CI tasks should usually be shorter than 10 minutes.
10. Contribute frequently to the same main line: Usually, everyone should make a commit at least once per day.

By implementing these best practices, software development teams can improve code quality, accelerate the development process, and deliver better software products to their customers.

## 2. Our Github Action 1: Maven Build and Test
### 2.1 Configuration
We added the GitHub Action configuration file `.github/workflows/mvn-test.yml` with the content below:

```yml
name: Java CI

on: [push]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'
      - name: Build with Maven
        run: mvn clean test
```

It means that upon a push to any branches, there will be a runner with ubuntu 22.04 operating system that:
1. download a copy of our source codes
2. configure the Eclipse Temurin Java 17 SDK by Eclipse Adoptium
3. run all the test cases

### 2.2 Outcome

The Github Action turns out a pass, but with some annotations of error. We checked the log file and confirmed that all test cases were passed.

```text
2023-02-23T21:40:38.5483549Z Results :
2023-02-23T21:40:38.5483723Z 
2023-02-23T21:40:38.5483872Z Tests run: 12126, Failures: 0, Errors: 0, Skipped: 231
```

We think the annotations may come from some test cases designated to fail.

Besides, it takes 27 minutes in total to run. It is usually considered too long for a CI process. However, the QuestDB existing build and test Azure pipelines (explained [below](#5-existing-azure-pipelines)) also take around 10 - 20 minutes. Considering the large scale of QuestDB and that we didn't pay for higher performance runners to Github, the 27 minutes processing time is reasonable and we cannot really do anything to improve that.

## 3. Our Github Action 2: Markdown To PDF

When we were doing project part 2, we already added a Github Action (the [source codes](https://github.com/BaileyJM02/markdown-to-pdf)) to convert our report written in markdown to pdf automatically on each push to master.

### 3.1 Configuration


We added the GitHub Action configuration file `.github/workflows/convert-to-pdf.yml` with the content below:

```yml
# .github/workflows/convert-to-pdf.yml

name: Docs to PDF
# This workflow is triggered on pushes to the repository.
on:
  push:
    branches:
     - master
    # Paths can be used to only trigger actions when you have edited certain files, such as a file within the /docs directory
    paths:
      - 'docs/**'
      - '.github/**'

jobs:
  convert-to-pdf:
    name: Build PDF
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: baileyjm02/markdown-to-pdf@v1
        with:
          input_dir: docs
          output_dir: docs/
          images_dir: docs/images
          image_import: ./images
          build_html: false
      - uses: actions/upload-artifact@v1
        with:
          name: theGeneratedPDF
          path: docs
```

It means that upon push to the master branch, if there are changes in the path `docs/**` or `.github/**`, there will be a runner with ubuntu 22.04 operating system that:
1. download a copy of our source codes
2. convert all the markdown files in the path `docs` to pdf files, supposing the images are in `docs/images` folder, and output the pdf files to the directory `docs/`
3. upload the folder `docs` as a zipped file

### 3.2 Outcome
We didn't succeed at the first trial. After adding some logging commands in the GitHub Action configuration file, including the `ls -R`, we find that it is because we put our markdown files in the folder `.github/` but the folder is not downloaded to the runner who runs the action.

But even if we put our markdown files elsewhere, it did not succeed either. We specified the `output_dir` as `docs`, but it outputs the pdf file called `docREADME.md` to the project root. Therefore, since we changed the `output_dir` to `docs/`, it works well now.

They have clearly specified in their documentation that we should declare our path without the `/` suffix. Therefore, we think it is a bug in their logic, in their source code file `src/github_interface.js` around line 64. Hence, we planned to fix that bug and contribute to this GitHub Action source code in the future when we have time.

## 4. Existing Github Action: Danger - Validate PR Title
QuestDB's original repository already defined a Github Action to validate their pull request title.

They use a CI tool called [Danger](https://danger.systems/js/). Danger is a tool to automate the common code review chores and leave messages inside the pull requests based on the rules defined in scripts.

In this case, they let Danger validate their pull request titles formatting, with Danger configuration file `ci/validate-pr-title/dangerfile.js`, and formatting rules in `ci/validate-pr-title/validate.js`. Basically, it should be in the format `type(subtype):  description`. The allowed types and subtypes can be found in `ci/validate-pr-title/validate.js`. For example, `fix(config) add GitHub action` will be a valid title. If the title is invalid, the GitHub Action will fail.

Besides, they wrote some tests to test the formatting rule itself in `ci/validate-pr-title/validate.test.js`.

The Github Action configuration file is in `.github/workflows/danger.yml`, with the content below:

```yml
name: Danger

on:
  pull_request:
    types: [synchronize, opened, reopened, edited]

jobs:
  build:
    if: ${{ github.event.pull_request.head.repo.full_name == github.repository }} # Only run on non-forked PRs
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@master
      - name: Use Node.js 16.x
        uses: actions/setup-node@master
        with:
          node-version: 16.x
      - name: install danger 
        run: yarn global add danger 
      - name: Validate PR title validation rules
        working-directory: ./ci/validate-pr-title
        run: node validate.test.js
      - name: Danger
        run: danger ci
        working-directory: ./ci/validate-pr-title
        env:
          DANGER_GITHUB_API_TOKEN: ${{ secrets.DANGER_GITHUB_TOKEN }}
```

It means if there is a non-forked pull request in type synchronize, opened, reopened, or edited, there will be a runner with ubuntu 22.04 operating system that:

1. download a copy of our source codes
2. setup Node.js environment
3. install the `danger` tool
4. run the tests in `ci/validate-pr-title/validate.test.js` to test the formatting rule
5. let Danger validate the pull request title

## 5. Existing Azure Pipelines

QuestDB has many existing CI processes hosted in Azure pipelines. All the relevant files are in the directory `ci/`. The most important two are `ci/template/hosted-jobs.yml` and `ct/templates/hosted-cover-jobs.yml`, both are linked from `ci/test-pipeline.yml`.

The `ci/template/hosted-jobs.yml` run the test cases in macos-latest, windows-lates, and ubuntu-latest operating systems respectively. They also split all the test cases into three groups -- griffin, cairo, and others and run them separately.

The `ct/templates/hosted-cover-jobs.yml` utilize the Jacoco tool to measure the test coverage and generate coverage reports automatically. Only ubuntu will run this task and the tests are also separated into griffin, cairo, and others.

As their Azure pipelines are not watching our repository, which is forked from their official repository, no runners really run these checks upon the changes in our repository.

But as we created a new pull request to their official repository to contribute to that backslash escape feature in LIKE statements, their runners ran the CI processes and some failed. As we have no access to the log statements, we would communicate with them further on what we should do next.


<div style="page-break-after: always;"></div>


# Part 5. Testable Design. Mocking of QuestDB


**Table of Contents**
+ [1. Testable Design](#1-testable-design)
    + [1.1 Aspects to Make a Testable Design](#11-aspects-to-make-a-testable-design)
    + [1.2 Goals to Make a Testable Design](#12-goals-to-make-a-testable-design)
+ [2. Mocking](#2-mocking)
    + [2.1 Definition of Mocking](#21-definition-of-mocking)
    + [2.2 The Utility of Mocking](#22-the-utility-of-mocking)
    + [2.3 Mock Testing vs Traditional Unit Testing](#23-mock-testing-vs-traditional-unit-testing)
+ [3. Documentation of Existing Code](#3-documentation-of-existing-code)
    + [3.1 A Difficult-Testing Code Example](#31-a-difficult-testing-code-example)
    + [3.2 Advice to Fix the Code and Implementation](#32-advice-to-fix-the-code-and-implementation)
+ [4. New Test Cases](#4-new-test-cases)
    + [4.1 Mocking](#41-mocking)
    + [4.2 Testable Design](#42-testable-design)

----------
<p style="page-break-after:always"></p>


# 1. Testable Design

## 1.1 Aspects to Make a Testable Design

When it comes to designing a system or software, there are many aspects that can make it more testable. Some of these include creating modular and cohesive code, using consistent naming conventions, minimizing dependencies, etc. Additionally, designing with testability in mind from the start can make it easier to create and execute tests, as well as identify and fix issues more quickly. Overall, a testable design is one that is structured, organized, and designed to facilitate testing at every stage of the development process.

The professor emphasized that creating a testable design is crucial for ensuring software quality and reliability. By avoiding certain coding practices, such as using **private complex methods**, **static methods**, **hardcoding in "new" statements**, **logic in constructors**, and **the singleton pattern**, developers can create more modular and testable code that is easier to maintain and improve over time. Other aspects that can help us achieve testable design are as follows.

- **Modularity**: A testable design is often broken down into smaller modules or components. These modules are designed to be self-contained and have well-defined interfaces, which makes them easier to test in isolation.
- **Separation of concerns**: A testable design separates different concerns or responsibilities into different modules or components. This ensures that each module is responsible for a specific task, which makes it easier to test and debug.
- **Loose coupling**: A testable design avoids tight coupling between different modules or components. This means that each module or component should be able to function independently of other modules, which makes it easier to test them in isolation.
- **Encapsulation**: A testable design encapsulates the internal state of each module or component. This means that the state of a module can only be modified through a well-defined interface. This makes it easier to test the module, as the internal state can be controlled and verified.
- **Clear and concise interfaces**: A testable design has clear and concise interfaces between different modules or components. This makes it easier to test each module in isolation, as the inputs and outputs of each module are well-defined.
- **Error handling**: A testable design has well-defined error handling mechanisms. This means that the design should be able to handle errors and exceptions in a consistent and predictable manner, which makes it easier to test and debug.

## 1.2 Goals to Make a Testable Design

A testable design should be built with the goal of ensuring that the design meets the required specifications and is robust, reliable, and efficient.

- **Facilitate testing**: A testable design is designed to be easily and effectively tested. This means that it should be easy to write test cases for each module or component, and that these test cases should be able to verify the correct functioning of the design.
- **Improve reliability**: A testable design is designed to be reliable. This means that it should be able to handle errors and exceptions in a consistent and predictable manner, and that it should be able to function correctly in different environments and conditions.
- **Simplify maintenance**: A testable design is designed to be easy to maintain. This means that it should be easy to modify and update each module or component without affecting other parts of the design.

# 2. Mocking

## 2.1 Definition of Mocking

Software testing mocking is a technique that involves creating fake or simulated objects, functions, or services to replicate the behavior of the real components of a software application. These simulated components, called "mocks," are designed to emulate the behavior of the real components and allow software developers and testers to isolate and test specific parts of an application in isolation. Mock is useful for interaction testing, as opposed to state testing.

## 2.2 The Utility of Mocking

The utility of mocking lies in its ability to help developers and testers ensure that their software applications are functioning as intended. By using mocks, developers can simulate the behavior of the various components of an application and test them in isolation, without having to worry about the behavior of other parts of the system. This can help to identify bugs and other issues that may be difficult to find in a complex, integrated system. In addition, mocking can help to speed up the software development process by allowing developers to test their code more quickly and efficiently. By isolating specific parts of an application and testing them in isolation, developers can quickly identify and fix issues before moving on to the next phase of development.

The main advantages of mock testing are:

- **Isolation of code**: Mock testing allows developers to isolate and test specific parts of an application without having to worry about the behavior of other parts of the system. This can help to identify bugs and other issues that may be difficult to find in a complex, integrated system.
- **Speed of testing**: Since mock testing focuses on specific parts of an application, it can help developers to test their code more quickly and efficiently. By using mocks, developers can simulate the behavior of various components and test them in isolation, without having to wait for the entire system to be up and running.
- **Reduces dependencies**: Mock testing can help to reduce dependencies on external services or resources. By creating mock objects or functions, developers can test their code without having to rely on external resources that may be difficult to set up or access.
- **Easier to maintain**: Mock testing can make it easier to maintain and update code. By using mocks, developers can make changes to specific parts of an application without having to worry about the behavior of other parts of the system. This can make it easier to update code and fix issues.

## 2.3 **Mock Testing vs Traditional Unit Testing**

Below are some of the [differences between mock testing and traditional unit testing](https://www.geeksforgeeks.org/software-testing-mock-testing/):

| Mock Testing | Traditional Unit Testing |
| --- | --- |
| In mock testing, assertions are done by mock objects and no assertions are required from unit testing | While in traditional unit testing assertions are done for dependencies. |
| In mock testing, it is focused on how the fake/mock object can be incorporated and tested. | In traditional unit testing, it was focused on how the real object can be incorporated and tested. |
| Mock testing is more about behavior-based verification. | Traditional unit testing is more about state-based verification. |

# 3. Documentation of Existing Code

## 3.1 A Difficult-Testing Code Example

QuestDB is considered to be a well-designed and testable system due to several factors, like **modular architecture**, **code simplicity**, **strong adherence to standards**, **continuous integration and testing**, **open-source community**, etc. QuestDB's design and development practices make it a well-designed and testable system that is easy to work with and maintain. This, in turn, makes it a popular choice for developers who are looking for a fast, reliable, and scalable database solution. Its current test coverage is already very high (96% for class coverage, 90% for method coverage, 91% for line coverage). We tried hard to find some untestable cases in QuestDB based on the 5 main rules in testable design that mentioned in class:

1. **Avoid complex private methods**: complex logic in private methods can be a source for bugs that cannot be found by direct testing.
2. **Avoid static methods**: static methods are methods that belong to a class rather than an instance of the class (i.e. object). They can make it difficult to isolate and test the behavior of a component.
3. **Be careful hardcoding in “new”**: We need to allow the object reference to be created outside the method and passed in (dependency injection).
4. **Avoid logic in constructors**: better to have a more simple constructor and have the functionality placed in another method.
5. **Avoid singleton pattern**: there is appropriate use of this pattern, however, make sure that it is something that does not need to be swapped out for testing.

While its design is very testable and robust, we found a case in QuestDB where the code does not align with testable design principle that a “new” is being used within a method. Their current code is:

**Original `IODispatcherLinux` class**

```java
public class IODispatcherLinux<C extends IOContext<C>> extends AbstractIODispatcher<C> {
    private static final int EVM_DEADLINE = 1;
    private static final int EVM_ID = 0;
    private static final int EVM_OPERATION_ID = 2;
    protected final LongMatrix pendingEvents = new LongMatrix(3);
    private final Epoll epoll;
    // the final ids are shifted by 1 bit which is reserved to distinguish socket operations (0) and suspend events (1);
    // id 0 is reserved for operations on the server fd
    private long idSeq = 1;

    public IODispatcherLinux(
            IODispatcherConfiguration configuration,
            IOContextFactory<C> ioContextFactory
    ) {
        super(configuration, ioContextFactory);
        this.epoll = new Epoll(configuration.getEpollFacade(), configuration.getEventCapacity());
        registerListenerFd();
    }
```

**Original `Epoll` class**

```java
public final class Epoll implements Closeable {
    private static final Log LOG = LogFactory.getLog(Epoll.class);

    private final int capacity;
    private final EpollFacade epf;
    private final int epollFd;
    private final long events;
    private long _rPtr;
    private boolean closed = false;

    public Epoll(EpollFacade epf, int capacity) {
        this.epf = epf;
        this.capacity = capacity;
        this.events = this._rPtr = Unsafe.calloc(EpollAccessor.SIZEOF_EVENT * (long) capacity, MemoryTag.NATIVE_IO_DISPATCHER_RSS);
        this.epollFd = epf.epollCreate();
        if (epollFd < 0) {
            throw NetworkError.instance(epf.errno(), "could not create epoll");
        }
        Files.bumpFileCount(this.epollFd);
    }
```

Epoll is a scalable I/O event notification mechanism in Linux operating system. It is used to monitor multiple file descriptors (sockets, file handles, etc.) to see if any of them have I/O events pending. Epoll is a replacement for the older and less scalable `select` and `poll` mechanisms, and it provides better performance in high-volume and high-concurrency I/O applications. Epoll is commonly used in network servers, where it can handle thousands or even millions of client connections efficiently.

In the context of QuestDB, Epoll is used in the `IODispatcherLinux` class to handle I/O events on network sockets. The class uses Epoll to efficiently manage the selection of sockets that are ready for I/O operations, allowing QuestDB to handle a large number of concurrent network connections with low latency and high throughput.

However, in the original implementation of class `IODispatcherLinux` of QuestDB (src/main/java/io/questdb/network/IODispatcherLinux.java), it is not possible to test the interaction between `IODispatcherLinux` and `Epoll` for the following reasons:

1. `Epoll` is a `private final` field of `IODispatcherLinux`, meaning that it cannot be directly accessed or modified from outside the class. This makes it difficult to write tests that can verify the behavior of the `IODispatcherLinux` **class when interacting with the `Epoll` field**.

2. `Epoll` is instantiated as a `new` object in the constructor of `IODispatcherLinux`, which means that it is tightly coupled to the implementation of `IODispatcherLinux`. This makes it difficult to substitute `Epoll` **with a test double or a mock object, which would allow for more flexible and targeted testing**.

3. `Epoll` is a `final` class. Since a `final` class cannot be extended or subclassed, it cannot be mocked using most mocking frameworks, which typically rely on subclassing or extending the original class to create a mock object.

## 3.2 Advice to Fix the Code and Implementation

Since class `Epoll` and `IODispatcherLinux` are closely coupled, `Epoll` can not be mocked and the interaction between `IODispatcherLinux` and `Epoll` can not be directly tested. Therefore, we changed the codes into a testable design by allowing the `Epoll` object reference to be created outside the `IODispatcherLinux` method and passed in (i.e. dependency injection).

We modified the code in the following steps:

1. Change the `Epoll` field from `private final` to `protected final`. By changing a `private` field to `protected`, we allow subclasses and other classes in the same package to access the field. This means that we can write tests which subclass the object under test or place the test class in the same package, and access the field directly to set up the object's state for testing. This makes the object more testable, without compromising its encapsulation or making the field fully `public`.

2. Overload the constructor of `IODispatcherLinux` so that a mock object of `Epoll` can be passed in. By overloading the constructor of the class, we allow a mock object to be passed in as a parameter to the constructor. The developer can isolate and test individual components of the code without needing to involve all the dependencies of the component being tested.

3. Deleted the `final` modifier of `Epoll` class. As a final class cannot be mocked, we suggest to delete the final modifier to make Epoll testable.

**Modified `IODispatcherLinux` class**

```java
public class IODispatcherLinux<C extends IOContext<C>> extends AbstractIODispatcher<C> {
    private static final int EVM_DEADLINE = 1;
    private static final int EVM_ID = 0;
    private static final int EVM_OPERATION_ID = 2;
    protected final LongMatrix pendingEvents = new LongMatrix(3);
    protected final Epoll epoll;
    // the final ids are shifted by 1 bit which is reserved to distinguish socket operations (0) and suspend events (1);
    // id 0 is reserved for operations on the server fd
    private long idSeq = 1;

    public IODispatcherLinux(
            IODispatcherConfiguration configuration,
            IOContextFactory<C> ioContextFactory,
            Epoll epoll
    ) {
        super(configuration, ioContextFactory);
        this.epoll = epoll;
        registerListenerFd();
    }

    public IODispatcherLinux(
            IODispatcherConfiguration configuration,
            IOContextFactory<C> ioContextFactory
    ) {
        super(configuration, ioContextFactory);
        this.epoll = new Epoll(configuration.getEpollFacade(), configuration.getEventCapacity());
        registerListenerFd();
    }
```

Modified `Epoll` class

```java
public class Epoll implements Closeable {
    private static final Log LOG = LogFactory.getLog(Epoll.class);

    private final int capacity;
    private final EpollFacade epf;
    private final int epollFd;
    private final long events;
    private long _rPtr;
    private boolean closed = false;

    public Epoll(EpollFacade epf, int capacity) {
        this.epf = epf;
        this.capacity = capacity;
        this.events = this._rPtr = Unsafe.calloc(EpollAccessor.SIZEOF_EVENT * (long) capacity, MemoryTag.NATIVE_IO_DISPATCHER_RSS);
        this.epollFd = epf.epollCreate();
        if (epollFd < 0) {
            throw NetworkError.instance(epf.errno(), "could not create epoll");
        }
        Files.bumpFileCount(this.epollFd);
    }
```

Mocking the `Epoll` object would allow for a type of behavior checking that may not be afforded without mocking because it would allow for isolated testing of the `IODispatcherLinux` object's behavior when interacting with the `Epoll` object.

In the original code where `Epoll` and `IODispatcherLinux` were closely coupled, it would be difficult to test the behavior of `IODispatcherLinux` without also testing the behavior of `Epoll`, and any errors or issues with `Epoll` could potentially cause false positives or negatives in testing the behavior of `IODispatcherLinux`. After modifying the current source codes to a testable design, we are able to mock the `Epoll` object. As a result, the behavior of `IODispatcherLinux` can be tested in isolation from the behavior of `Epoll`, allowing for more accurate testing of `IODispatcherLinux`'s behavior.

# 4. New Test Cases

## 4.1 Mocking

We added 2 test cases in `io.questdb.network.MockedEpollTest` to test the interaction between `IODispatcherLinux` and `Epoll` using the Mockito framework.

First, the `epoll` and `ioContextFactory` are mocked. The `ioDispatcherLinux` object is instantiated with the help of the mocked objects. Next, the function `io.questdb.network.IODispatcherLinux#registerListenerFd` is tested. This function should have been called once when the object `ioDispatcherLinux` is constructed. As a part of the `registerListenerFd` function, `io.questdb.network.Epoll#listen` should have been called once on the mocked `epoll` object. After being explicitly called in the test case, it should have been called twice. They are verified by the `verify` function provided by the Mockito framework.

Lastly, the function `io.questdb.network.IODispatcherLinux#unregisterListenerFd` is tested. As a part of the function, `io.questdb.network.Epoll#removeListen` should have been called once. It is also verified by the `verify` function from Mockito. As the file descriptor may change in different environments, `anyInt()` is used in the verification instead of a specific integer.

To conclude, the interaction between the `IODispatcherLinux` and `Epoll`, specifically the process of registering and unregistering the listener file descriptors behave as expected and is verified with the help of the mocked objects.

```java
public class MockedEpollTest {

    private static Epoll epoll;
    private static IODispatcherLinux ioDispatcherLinux;
    private static IOContextFactory<HttpConnectionContext> ioContextFactory;
    
    @BeforeClass
    public static void setUp(){
        epoll = mock(Epoll.class);
        IODispatcherConfiguration configuration = new DefaultIODispatcherConfiguration();
        ioContextFactory = mock(IOContextFactory.class);
        ioDispatcherLinux = new IODispatcherLinux(configuration, ioContextFactory, epoll);
    }

    @Test
    public void testRegister(){
        verify(epoll, times(1)).listen(anyInt()); // once in constructor
        ioDispatcherLinux.registerListenerFd();
        verify(epoll, times(2)).listen(anyInt());
    }

    @Test
    public void testUnregister(){
        ioDispatcherLinux.unregisterListenerFd();
        verify(epoll, times(1)).removeListen(anyInt());
    }
}
```

## 4.2 Testable Design

To make the `Epoll` object work on a MacOS machine, we need to use a mock object because the `IoDispatcherLinux` can only be initialized on a Linux machine. Thus, to create testable code, we must mock objects.

We added 1 JUnit test cases in `io.questdb.network.MockedEpollTest` to test the functionality of `IODispatcherLinux` and `Epoll`. First, we initialized the `ioDispatcherLinux` object with the mocked `Epoll` and `ioContextFactory`. We then tested the `io.questdb.network.IODispatcherLinux#close` function to confirm that the `ioDispatcherLinux` can be closed properly, and `Epoll.closed()` is called as expected.

By implementation of these tests, we increased the method coverage of `Epoll` and `IoDispatcherLinux` by 10% and 15% respectively, which was 0% previously.

```java
public class MockedEpollTest {

    private static Epoll epoll;
    private static IODispatcherLinux ioDispatcherLinux;
    private static IOContextFactory<HttpConnectionContext> ioContextFactory;

    @BeforeClass
    public static void setUp(){
        epoll = mock(Epoll.class);
        IODispatcherConfiguration configuration = new DefaultIODispatcherConfiguration();
        ioContextFactory = mock(IOContextFactory.class);
        ioDispatcherLinux = new IODispatcherLinux(configuration, ioContextFactory, epoll);
    }
		
    // Test methods in Class ioDispatcherLinux
    @Test
    public void testCloseioDispatcherLinux() {
        ioDispatcherLinux.close();
        Assert.assertTrue("closed", ioDispatcherLinux.closed);
	verify(ioDispatcherLinux.epoll, times(1)).close();
    }
}
```

<div style="page-break-after: always;"></div>


# Part 6: Static Analyzers of QuestDB

**Table of Contents**
+ [1. Static Analyzers](#1-static-analyzers)
+ [2. Implementation of SpotBugs](#2-implementation-of-spotBugs)
    + [2.1 Introduction of SpotBugs](#21-introduction-of-spotBugs)
    + [2.2 The result of SpotBugs](#22-the-result-of-spotBugs)
        + [2.2.1 Bad Practice Warnings: 18 items](#221-bad-practice-warnings)
        + [2.2.2 Correctness Warnings: 90 items](#222-correctness-warnings)
        + [2.2.3 Internationalization Warnings: 3 items](#223-internationalization-warnings)
        + [2.2.4 Malicious code vulnerability Warnings: 571 items](#224-malicious-code-vulnerability-warnings)
        + [2.2.5 Multithreaded correctness Warnings: 1 item](#225-multithreaded-correctness-warnings)
        + [2.2.6 Performance Warnings: 14 items](#226-performance-warnings)
        + [2.2.7 Dodgy code Warnings: 117 items](#227-dodgy-code-warnings)
+ [3. Implementation of Checkstyle](#3-implementation-of-checkstyle)
    + [3.1 Introduction of Checkstyle](#31-introduction-of-checkstyle)
    + [3.2 The result of Checkstyle](#32-the-result-of-checkstyle)
        + [3.2.1 “Error” Analysis of BinarySearch Class](#321-error-analysis-of-binarySearch-class)
        + [3.2.2 Not Real Errors but Violate sun_check Rules](#322-not-real-errors-but-violate-sun_check-rules)
+ [4. Comparison of SpotBugs and CheckStyle](#4-comparison-of-spotBugs-and-checkStyle)
    + [4.1 Same Warning on Hidden Field `txn`](#41-same-warning-on-hidden-field-`txn`)
    + [4.2 Distinct Warnings](#42-distinct-warnings)

---
<p style="page-break-after:always"></p>

# <a name="1-static-analyzers"></a>1. Static Analyzers

Static analysis tools, also known as static analyzers or compile-time analysis, are software programs that examine source code or compiled object code without actually executing it. These tools examine the code to identify potential errors, security vulnerabilities, and other issues that could cause problems in the program's behavior.

The primary purposes of static analysis tools are to:

1. **Improve code quality**: Static analysis tools help developers identify potential issues in their code before it is executed. This reduces the likelihood of bugs and other issues appearing in the final product, improving the overall quality of the code.
2. **Enhance security**: Static analysis tools can detect potential security vulnerabilities in code, such as buffer overflows, input validation issues, and SQL injection attacks. By detecting these issues early in the development process, developers can address them before they become real security threats.
3. **Increase productivity**: By detecting potential issues early in the development process, developers can save time and effort that would otherwise be spent on debugging and fixing errors.

Static analysis tools can be used in a variety of ways, including:

1. **Code reviews**: Static analysis tools can be used to perform automated code reviews, which can identify potential issues that may have been missed during manual code reviews.
2. **Continuous Integration/Continuous Delivery (CI/CD)**: Static analysis tools can be integrated into CI/CD pipelines to identify issues in code before it is deployed to production. This helps ensure that software is of high quality and free from vulnerabilities.
3. **Compliance**: Static analysis tools can be used to ensure that code meets certain compliance standards, such as those set forth by regulatory bodies or industry organizations.

# <a name="2-implementation-of-spotBugs"></a>2. Implementation of SpotBugs

## <a name="21-introduction-of-spotBugs"></a>2.1 Introduction of SpotBugs

[SpotBugs](https://spotbugs.github.io/) is an open-source static code analysis tool designed to find potential bugs in Java code. It is distributed under the terms of the [GNU Lesser General Public License](http://www.gnu.org/licenses/lgpl.html). It uses advanced algorithms to analyze Java bytecode and identify various types of issues such as null pointer dereferences, deadlocks, infinite loops, and more.

In this report, we use the [SpotBugs](https://plugins.jetbrains.com/plugin/14014-spotbugs) plugins provided in the Intellij marketplace to identify potential errors in QuestDB.

## <a name="22-the-result-of-spotBugs"></a>2.2 The result of SpotBugs

The output was generated by SpotBugs 4.4.2, which primarily analyzed the core project located in the root directory (questdb_test_debug/core). Based on the "Metrics" report, the analysis covered 135,895 lines of code across 2,795 classes and 74 packages. The "Summary" indicates that a total of 814 warnings were detected. The definitions of each warning are:

- [Bad practice Warnings](https://spotbugs.readthedocs.io/en/latest/bugDescriptions.html#bad-practice-bad-practice): There are 18 warnings were found. It refers to violations of recommended and essential coding practice. Examples include hash code and equals problems, cloneable idiom, dropped exceptions, Serializable problems, and misuse of finalize. We strive to make this analysis accurate, although some groups may not care about some of the bad practices.
- [Correctness Warnings](https://spotbugs.readthedocs.io/en/latest/bugDescriptions.html#correctness-correctness): There are 90 warnings were found. It refers to probable bug - an apparent coding mistake resulting in code that was probably not what the developer intended. We strive for a low false positive rate.
- [Internationalization Warnings](https://www.notion.so/SWE-261P-Project-Part-6-Static-Analyzers-OR-Mutation-Testing-of-QuestDB-a4af3cb74019483b8b49722c0b61d6fb): There are 3 warnings were found. It refers to code flaws having to do with internationalization and locale.
- [Malicious code vulnerability Warnings](https://spotbugs.readthedocs.io/en/latest/bugDescriptions.html#malicious-code-vulnerability-malicious-code): There are 571 warnings were found. It refers to code that is vulnerable to attacks from untrusted code.
- [Multithreaded correctness Warnings](https://spotbugs.readthedocs.io/en/latest/bugDescriptions.html#multithreaded-correctness-mt-correctness): There are 1 warning was found. It refers to code flaws having to do with threads, locks, and volatiles.
- [Performance Warnings](https://spotbugs.readthedocs.io/en/latest/bugDescriptions.html#performance-performance): There are 14 warnings were found. It refers to code that is not necessarily incorrect but may be inefficient.
- [Dodgy code Warnings](https://spotbugs.readthedocs.io/en/latest/bugDescriptions.html#dodgy-code-style): There are 117 warnings were found. It refers to code that is confusing, anomalous, or written in a way that leads itself to errors. Examples include dead local stores, switch fall through, unconfirmed casts, and redundant null check of value known to be null. More false positives accepted. In previous versions of SpotBugs, this category was known as Style.

In the following chapters, we selected some of the most common warnings of each type to discuss in further detail.

**Metrics**

135,895 lines of code analyzed, in 2,795 classes, in 74 packages.

| Metric                   | Total | Density* |
| ------------------------ | ----- | -------- |
| High Priority Warnings   | 78    | 0.57     |
| Medium Priority Warnings | 736   | 5.42     |
| Total Warnings           | 814   | 5.99     |

*(* Defects per Thousand lines of non-commenting source statements)*

**Summary**

| Warning Type                          | Number |
| ------------------------------------- | ------ |
| Bad Practice Warnings                 | 18     |
| Correctness Warnings                  | 90     |
| Internationalization Warnings         | 3      |
| Malicious code vulnerability Warnings | 571    |
| Multithreaded correctness Warnings    | 1      |
| Performance Warnings                  | 14     |
| Dodgy code Warnings                   | 117    |
| Total                                 | 814    |

### <a name="221-bad-practice-warnings"></a>2.2.1 Bad Practice Warnings: 18 items

- **Class defines equals() and uses Object.hashCode(): 3 items, high priority warning**

| Warning | Priority | Details |
| --- | --- | --- |
| Class defines equals() and uses Object.hashCode() | High | io.questdb.std.BoolList defines equals and uses Object.hashCode() |

```java
public class BoolList implements Mutable {
    ...
    @Override
    public boolean equals(Object that) {
        return this == that || that instanceof BoolList && equals((BoolList) that);
    }
    ...
}
```

This class overrides `equals(Object)`, but does not override `hashCode()`, and inherits the implementation of `hashCode()` from `java.lang.Object` (which returns the identity hash code, an arbitrary value assigned to the object by the VM).  Therefore, the class is very likely to violate the invariant that equal objects must have equal hashcodes.

If you don't think instances of this class will ever be inserted into a HashMap/HashTable, the recommended `hashCode` implementation to use is:

```java
public int hashCode() {
    assert false : "hashCode not designed";
    return 42; // any arbitrary constant will do
}
```

if a class overrides the `equals()` method, it is important to also provide a proper implementation for `hashCode()` to avoid issues when using hash-based data structures. If a proper implementation cannot be provided, the recommended implementation is to throw an assertion error and return an arbitrary constant value.

In this case, we think that **this is not an actual problem, but it is better to revise the code.**

### <a name="222-correctness-warnings"></a>2.2.2 Correctness Warnings: 90 items

- **Method may return null, but is declared @Nonnull: 36 items, high priority warning**

| Warning | Priority | Details |
| --- | --- | --- |
| Method may return null, but is declared @Nonnull | High | io.questdb.PropServerConfiguration$PropSqlExecutionCircuitBreakerConfiguration.getClock() may return null, but is declared @Nonnull |

```java
private class PropSqlExecutionCircuitBreakerConfiguration implements SqlExecutionCircuitBreakerConfiguration {
    ...
    @Override
    @NotNull
    public MillisecondClock getClock() {
        return MillisecondClockImpl.INSTANCE;
    }
    ...
}
```

This method may return a null value, but the method (or a superclass method which it overrides) is declared to return @Nonnull. In this case, we think that **this is an actual problem**.

### <a name="223-internationalization-warnings"></a>2.2.3 Internationalization Warnings: 3 items

- **Reliance on default encoding: 3 items, high priority warning**

| Warning | Priority | Details |
| --- | --- | --- |
| Reliance on default encoding | High | Found reliance on default encoding in io.questdb.Bootstrap.getPublicVersion(String): new String(byte[], int, int) |

```java
public class Bootstrap {
    ...
    private static String getPublicVersion(String publicDir) throws IOException {
    File f = new File(publicDir, PUBLIC_VERSION_TXT);
    if (f.exists()) {
        try (FileInputStream fis = new FileInputStream(f)) {
            byte[] buf = new byte[128];
            int len = fis.read(buf);
            return new String(buf, 0, len);
        }
    }
    return null;
    ...
}
```

Found a call to a method which will perform a byte to String (or String to byte) conversion, and will assume that the default platform encoding is suitable. This will cause the application behavior to vary between platforms. In this case, **we believe that this is an actual problem.**

To fix this issue, it is recommended to use an alternative API that allows specifying the character encoding explicitly using a charset name or a Charset object. This will ensure consistent behavior across different platforms and environments. For example, modifying the code to:

```java
try (FileInputStream fis = new FileInputStream(f)) {
    byte[] buf = new byte[128];
    int len = fis.read(buf);
    Charset charset = StandardCharsets.UTF_8; // or another supported charset
    return new String(buf, 0, len, charset);
}
```

### <a name="224-malicious-code-vulnerability-warnings"></a>2.2.4 Malicious code vulnerability Warnings: 571 items

- **May expose internal representation by returning reference to mutable object: 278 items, medium priority warning**

| Warning | Priority | Details |
| --- | --- | --- |
| May expose internal representation by returning reference to mutable object | Medium | io.questdb.cairo.TableReaderSelectedColumnRecord.of(TableReader) may expose internal representation by storing an externally mutable object into TableReaderSelectedColumnRecord.reader |

```java
public class TableReaderSelectedColumnRecord implements Record {
    ...
    public void of(TableReader reader) {
        this.reader = reader;
    }
    ...
}
```

This code stores a reference to an externally mutable object into the internal representation of the object. If instances are accessed by untrusted code, and unchecked changes to the mutable object would compromise security or other important properties, you will need to do something different. In this case, **we believe that this is an actual problem.**

To address this issue, it may be better to store a copy of the object instead of a reference to the original object. This would ensure that the internal representation of the object is not affected by any modifications made to the original object. However, making a copy of a large or complex object can be expensive in terms of memory and performance, so it may not be feasible in all situations.

Alternatively, we can use defensive copying to create a copy of the object only when necessary, such as when the object is passed to untrusted code. This approach can help minimize the performance and memory impact of making a copy, while still providing protection against unauthorized modifications to the object. For example, modifying the code with the defensive copying:

```java
public void of(TableReader reader) {
    this.reader = new TableReader(reader); // defensive copy
}
```

### <a name="225-multithreaded-correctness-warnings"></a>2.2.5 Multithreaded correctness Warnings: 1 item

- **Inconsistent synchronization: 1 item, medium priority warning**

| Warning | Priority | Details |
| --- | --- | --- |
| Inconsistent synchronization | Medium | Inconsistent synchronization of io.questdb.cairo.TableNameRegistryRO.nameTableTokenMap; locked 50% of time |

```java
public class TableNameRegistryRO extends AbstractTableNameRegistry {
  ...
  private ConcurrentHashMap<TableToken> nameTableTokenMap = new ConcurrentHashMap<>(false);
  ...
  @Override
  public TableToken getTableToken(CharSequence tableName) {
      TableToken record = nameTableTokenMap.get(tableName);
      if (record == null && clockMs.getTicks() - lastReloadTimestampMs > autoReloadTimeout) {
          reloadTableNameCacheThrottled();
          return nameTableTokenMap.get(tableName);
      }
      return record;
   }
   ...
}
```

The fields of this class appear to be accessed inconsistently with respect to synchronization. This bug report indicates that the bug pattern detector judged that:

- The class contains a mix of locked and unlocked accesses,
- The class is **not** annotated as javax.annotation.concurrent.NotThreadSafe,
- At least one locked access was performed by one of the class's own methods, and
- The number of unsynchronized field accesses (reads and writes) was no more than one third of all accesses, with writes being weighed twice as high as reads

A typical bug matching this bug pattern is forgetting to synchronize one of the methods in a class that is intended to be thread-safe.

However, the code appears to address the thread-safety issue by using a `ConcurrentHashMap` to store the `TableToken` objects. This data structure provides thread-safe access to the underlying map, eliminating the need for explicit synchronization. As a result, the `getTableToken` method should now be thread-safe, as multiple threads can safely access and modify the map concurrently without any risk of race conditions or inconsistencies. In this case, **we think that it is not an actual problem**.

### <a name="226-performance-warnings"></a>2.2.6 Performance Warnings: 14 items

- **Explicit garbage collection; extremely dubious except in benchmarking code: 2 items, high priority warning**

| Warning | Priority | Details |
| --- | --- | --- |
| Explicit garbage collection; extremely dubious except in benchmarking code | High | new io.questdb.ServerMain(PropServerConfiguration, Metrics, Log, String) forces garbage collection; extremely dubious except in benchmarking code |

```java
public class ServerMain implements Closeable {
    ...
    public ServerMain(final PropServerConfiguration config, final Metrics metrics, final Log log, String banner) {
	...
	System.gc(); // GC 1
        log.advisoryW().$("bootstrap complete").$();
    }
    ...
}
```

Code explicitly invokes garbage collection. Except for specific use in benchmarking, this is very dubious. In the past, situations where people have explicitly invoked the garbage collector in routines such as close or finalize methods has led to huge performance black holes. Garbage collection can be expensive. Any situation that forces hundreds or thousands of garbage collections will bring the machine to a crawl.

However, there may be certain situations where invoking garbage collection can be helpful. For example, in applications with high memory usage or long-running processes, it may be useful to manually trigger garbage collection at certain intervals to prevent memory leaks or to ensure that memory is released in a timely manner.

According to [QuestDB](https://github.com/questdb/questdb/blob/master/CONTRIBUTING.md#allocations-new-operator-and-garbage-collection), it prioritizes zero-GC and has designed its system to minimize or even eliminate the amount of garbage collection needed during operation. Therefore, despite the fact that Spotbugs flagged it, **we do not consider it an actual problem** but rather a deliberate and carefully considered decision made by QuestDB.

### <a name="227-dodgy-code-warnings"></a>2.2.7 Dodgy code Warnings: 117 items

- **Dead store to local variable that shadows field: 1 item, high priority warning**

| Warning | Priority | Details |
| --- | --- | --- |
| Dead store to local variable that shadows field | High | Dead store to txn rather than field with same name in io.questdb.cairo.TableReaderTailRecordCursor.reload() |

```java
public class TableReaderTailRecordCursor extends TableReaderRecordCursor { 
    ...
    private long txn = TableUtils.INITIAL_TXN;
    ...
    public boolean reload() {
    long txn;
    if (reader.reload()) {
        if (reader.getDataVersion() != this.dataVersion) {
            lastRowId = -1;
            dataVersion = reader.getDataVersion();
            toTop();
        } else {
            seekToLastSeenRow();
        }
        txn = reader.getTxn();
        return true;
    }

    // when reader is created against table that already has data
    // TableReader.reload() would return 'false'. This method
    // must return 'true' in those conditions

    txn = reader.getTxn();

    if (txn > this.txn) {
        this.txn = txn;
        seekToLastSeenRow();
        return true;
    }
    return false;
    ...
}
```

This instruction assigns a value to a local variable `txn`, but the value is not read or used in any subsequent instruction. Often, this indicates an error, because the value computed is never used. There is a field with the same name as the local variable.

However, according to the code, it appears that the local variable `txn` is being used correctly in subsequent instructions. The first assignment of `txn` sets its value based on the result of calling the `getTxn()` method on the `reader` object, which is then used to compare with the `txn` field of the object.

In the case, **we believe this is not an actual problem**, as the code correctly uses the local variable `txn` to store the result of the method call and later compare it to the `txn` field of the object.

# <a name="3-implementation-of-checkstyle"></a>3. Implementation of Checkstyle

## <a name="31-introduction-of-checkstyle"></a>3.1 Introduction of Checkstyle

[Checkstyle](https://checkstyle.org) is a static analysis tool used to ensure that Java code conforms to a set of coding standards and guidelines. It checks the source code against a set of predefined rules and generates a report of violations. Checkstyle can be used to improve the readability, maintainability, and overall quality of Java code. Some of the benefits of using Checkstyle include improving code readability, reducing the likelihood of introducing errors or inconsistencies, and making code easier to maintain.

Main features of Checkstyle are as follows:

- Performs its analysis by parsing the Java source code and checking it against a set of pre-defined rules. These rules cover various aspects of coding standards, such as naming conventions, indentation, whitespace, and formatting.
- Can be integrated into various development environments, including IDEs like Eclipse and IntelliJ IDEA, and build tools like Maven and Gradle.
- Highly configurable, allowing users to customize the set of rules to be enforced according to their specific needs. It can also be extended with custom rules to cover domain-specific coding standards or best practices.
- Can detect certain logic mistakes in code, but **its primary purpose is to enforce coding conventions and style guidelines**. **It is not a substitute for unit testing or code analysis tools that are specifically designed to detect logic errors, bugs, or performance issues in code**. That being said, Checkstyle can help identify potential sources of bugs or readability issues in code, such as inconsistent naming conventions, unnecessary complexity, or code duplication.

## <a name="32-the-result-of-checkstyle"></a>3.2 The result of Checkstyle

The output was generated by Check 5.76.0, which primarily analyzed Java code in the core project located in the root directory (questdb_test_debug/core). The inspection result indicates that there are 77970 errors reported by Checkstyle 9.3 with sun_checks.xml ruleset in 1861 files. The report is located in `questdb_test_debug/core/target/site/checkstyle.html`.

```bash
// Command line to run Checkstyle

➜  core git:(master) mvn checkstyle:checkstyle
[INFO] Scanning for projects...
[INFO]
[INFO] ------------------------< org.questdb:questdb >-------------------------
[INFO] Building QuestDB core 7.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- maven-checkstyle-plugin:3.2.1:checkstyle (default-cli) @ questdb ---
[INFO] Rendering content with org.apache.maven.skins:maven-default-skin:jar:1.3 skin.
[INFO] There are 77970 errors reported by Checkstyle 9.3 with sun_checks.xml ruleset.
[WARNING] Unable to locate Source XRef to link to - DISABLED
[WARNING] Unable to locate Test Source XRef to link to - DISABLED
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  11.426 s
[INFO] Finished at: 2023-03-10T23:28:00-08:00
[INFO] ------------------------------------------------------------------------
➜  core git:(master)
```

As the figure below shows, in a Checkstyle report, each violation found is typically categorized by severity level into one of three categories: `info`, `warning`, or `error`.

Here's a brief explanation of each category:

- `info`: An informational message that does not necessarily indicate a problem with the code. For example, a violation might be categorized as “info” if it suggests a way to improve the code but is not strictly required.
- `warning`: A violation that indicates a potential problem with the code that could cause issues in the future. For example, a violation might be categorized as “warning” if it indicates a potential performance problem or a violation of best practices.
- `error`: A violation that indicates a problem with the code that needs to be fixed before the code can be considered correct. For example, a violation might be categorized as “error” if it indicates a violation of syntax or coding standards that could cause the code to behave incorrectly.

The severity level of each violation is typically indicated in the Checkstyle report using color coding or other visual indicators. In general, `error` violations should be addressed first, followed by `warning` violations, and then `info` violations if time permits. However, the severity of each violation should be considered in the context of the project's coding standards and priorities.

![](https://i.imgur.com/eFWSnWr.jpg)


As the report shows, Checkstyle has found a large number of errors (no “info” and “warning” here), specifically 77,970, which indicates that the project's codebase may need significant improvements to meet the specified coding standards. The predefined rulesets available for use in this version of the plugin are `[sun_checks.xml](https://checkstyle.org/sun_style.html)` and `[google_checks.xml](https://checkstyle.org/google_style.html)`, the default is sun_checks.xml. The number of “error” is extremely high, but it doesn’t mean they are actually errors in codes.

As shown below, when we click the file name in checkstyle.html, we can read the detail of each “error”: severity, category, rule, and message. Let’s dive into the codes and analyze some of them.

![](https://i.imgur.com/0oFZUk0.png)


```java
// src code of src/main/java/io/questdb/cairo/BinarySearch.java

package io.questdb.cairo;

import io.questdb.cairo.vm.api.MemoryR;

public class BinarySearch {
    // Down is increasing direction
    public static final int SCAN_DOWN = 1;
    // Up is decreasing direction
    public static final int SCAN_UP = -1;

    /**
     * Performs binary search on column of Long values.
     *
     * @param column        the column
     * @param value         the search value
     * @param low           the low boundary of the search, inclusive of value
     * @param high          the high boundary of the search inclusive of value
     * @param scanDirection logical direction in which column is searched. UP means we are looking for
     *                      the bottom boundary of the values that are lower or equal the search value. DOWN means
     *                      we are looking for upper boundary of the values that are greater or equal the search
     *                      value.
     * @return index in column where value is less or equal to the search value. If column contains
     * multiple exact matches the scanDirection determines whether top or bottom of these matches is returned.
     * When scan direction is DOWN - the last index of exact matches is returns, when UP - the first index
     */
    public static long find(MemoryR column, long value, long low, long high, int scanDirection) {
        while (low < high) {
            long mid = (low + high) / 2;
            long midVal = column.getLong(mid * Long.BYTES);

            if (midVal < value) {
                if (low < mid) {
                    low = mid;
                } else {
                    if (column.getLong(high * Long.BYTES) > value) {
                        return low;
                    }
                    return high;
                }
            } else if (midVal > value)
                high = mid;
            else {
                // In case of multiple equal values, find the first
                mid += scanDirection;
                while (mid > 0 && mid <= high && midVal == column.getLong(mid * Long.BYTES)) {
                    mid += scanDirection;
                }
                return mid - scanDirection;
            }
        }

        if (column.getLong(low * Long.BYTES) > value) {
            return low - 1;
        }
        return low;
    }
}
```

### <a name="321-error-analysis-of-binarySearch-class"></a>3.2.1 “Error” Analysis of BinarySearch Class

As the name “Checkstyle” suggests, this tool focuses more on the coding style of Java code rather than potential logic errors or warnings.

Checkstyle works by analyzing Java code and generating a report that highlights any violations of the configured rules. The rules are defined in an XML file that can be customized to fit the needs of the project. The rules cover a wide range of coding standards and best practices, including naming conventions, indentation, whitespace, class design, and more.

Most of the error in report are not real logic errors, given `src/main/java/io/questdb/cairo/BinarySearch.java` as an Example:

- Error 1: Utility classes should not have a public or default constructor. Utility classes are typically used to group together related methods and constants that are used throughout a codebase. Since these classes do not represent objects with state, they do not need to be instantiated, and therefore it is common to make their constructors private or protected. We can disable the default constructor of this class using code below:

```java
private MyClass() {
    // private constructor to disable instantiation
}
```

- Error 2: Missing a Javadoc comment. `JavadocVariable` is a Checkstyle rule that checks whether each non-private instance variable in a Java class has a Javadoc comment. If a Javadoc comment is missing, the Checkstyle report will flag the violation as an error or a warning. The purpose of this rule is to encourage developers to document the purpose and usage of each instance variable in their code. This can improve code readability and help other developers understand the intended usage of each variable. In fact, the src code already wrote some comments for these variables, but not in a standard java comment method, which can be modified as:

```java
/**
 * Down is increasing direction
 */
public static final int SCAN_DOWN = 1;
/**
 * Up is decreasing direction
 */
public static final int SCAN_UP = -1;
```

- Error 3: Line shouldn’t be longer than 80 characters. Code lines that are too long can be difficult to read and understand, especially if they contain multiple expressions or nested statements. Keeping lines shorter makes it easier to follow the code and understand its structure.

- Error 4: Marking a parameter as `final`. The `FinalParameters`Checkstyle rule checks whether parameters of methods, constructors, and catch blocks are marked as `final`. in a Java method indicates that the value of the parameter cannot be changed within the method. This rule encourages good coding practices by enforcing the use of `final` parameters, which can lead to safer and more maintainable code. We add add “final” keyword in front of the parameter column.

```java
public static long find(final MemoryR column, long value, long low, long high, int scanDirection) { 
... }
```

### <a name="322-not-real-errors-but-violate-sun_check-rules"></a>3.2.2 Not Real Errors but Violate sun_check Rules

According to our report, examples of rules that Checkstyle enforces mainly include:

- Method names should follow camelCase convention
- Class names should start with an uppercase letter and use PascalCase convention
- The number of method parameters should not exceed a certain limit
- There should be no whitespace at the end of a line
- Imports should be sorted alphabetically
- Constant names should be in all uppercase letters and use underscores to separate words
- Braces should always be used, even for single-line statements
- Method parameters should be listed on separate lines when there are more than two parameters
- The code should not contain magic numbers (i.e. hard-coded numeric values)
- The code should not contain too many nested blocks (i.e. loops, conditionals, etc.)
- The code should not contain unused imports or variables
- Class members (i.e. fields and methods) should have an access modifier (i.e. public, private, protected, or package-private)

After running Checkstyle on the codebase, **no major logic errors or bugs were identified that required immediate attention**. The report generated by Checkstyle did not flag any critical issues that would prevent the code from functioning correctly, and instead focused on enforcing coding conventions and best practices to ensure readability and maintainability of the code.

While Checkstyle can help identify potential sources of bugs or issues, **its primary purpose is to ensure consistency and adherence to coding standards**. Therefore, not finding any major logic problems in the Checkstyle report is a positive sign, but **it does not necessarily mean that the code is bug-free or that there are no logical errors that require further investigation or testing**. It is important to use Checkstyle in conjunction with other tools and practices, such as unit testing, code reviews, and debugging, to ensure the overall quality and correctness of the code.

# <a name="4-comparison-of-spotBugs-and-checkStyle"></a>4. Comparison of SpotBugs and CheckStyle

Although SpotBugs and CheckStyle are both static analysis tools, they focus on different aspects of code quality and have different rule sets. As a result, their output on QuestDB have little overlap.

SpotBugs is a tool that identifies potential bugs in Java code. It analyses the bytecode of compiled Java classes to detect issues such as null pointer dereferences, use of uninitialized variables, and concurrency issues. SpotBugs uses a set of rules to identify these potential bugs, and the rules are customizable. The rules in SpotBugs are focused on finding issues related to code correctness, rather than style or formatting.

CheckStyle, on the other hand, is a tool that enforces coding conventions and style guidelines. It checks for issues such as indentation, naming conventions, and code layout. CheckStyle uses a set of rules to identify violations of these conventions, and the rules are also customizable. The rules in CheckStyle are focused on enforcing coding style guidelines, rather than finding potential bugs.

Because the rulesets for SpotBugs and CheckStyle are different, the issues that they identify in QuestDB have little overlap. Sometimes they found the same issue with different angles, as in the `txn` example demonstrated below.

## <a name="41-same-warning-on-hidden-field-`txn`"></a>4.1 Same Warning on Hidden Field `txn`

In the section 2.2.7 Dodgy code Warnings, we documented the warning raised by SpotBugs on the hidden `txn` field. CheckStyle also raises the same issue, in a slightly different way.

```java
public boolean reload() {
    long txn;
    if (reader.reload()) {
        if (reader.getDataVersion() != this.dataVersion) {
            lastRowId = -1;
            dataVersion = reader.getDataVersion();
            toTop();
        } else {
            seekToLastSeenRow();
        }
        txn = reader.getTxn();
        return true;
    }

    txn = reader.getTxn();

    if (txn > this.txn) {
        this.txn = txn;
        seekToLastSeenRow();
        return true;
    }
    return false;
}
```

| Tools | Code | Warning |
| --- | --- | --- |
| SpotBugs | txn = reader.getTxn(); | Dead store to txn rather than field with same name |
| CheckStyle | long txn; | 'txn' hides a field. [HiddenField] |

This issue comes from QuestDB’s implementation that they create a local variable of `txn` and compare it with the `this.txn` to determine whether they should update `this.txn` to the local `txn`. It may be confusing. Both tools report this issue, while in a different way. The different output of SpotBugs and CheckStyle clearly demonstrate the different focus of SpotBugs and CheckStyle:

- When storing the `reader.getTxn()` to the local `txn` but never use it later, they may mean to assign it to `this.txn` instead. This may be a source of bug, but nothing relevant to styling.
- When declaring a variable with the same name of a field `long txn;`, it is a naming problem. They can simply rename the local `txn` to another name like `localTxn` to make their codes clearer and more readable. It does not necessarily leads to a bug.

## <a name="42-distinct-warnings"></a>4.2 Distinct Warnings

As a tool to find bugs instead of styling, SpotBugs never identify:

- Magic numbers (hard-coded numbers)
- Long one-line code
- Missing documentation

As a tool to check styling conventions instead of source of bugs, CheckStyle never identify:

- Redundant Comparison to null
- Bad casts of object references
- Method may return null, but is declared @Notnull

To summarize, it's important to note that the two tools have different focus and both tools have their own strengths and weaknesses. Using both tools together can provide a more comprehensive analysis of code quality.
