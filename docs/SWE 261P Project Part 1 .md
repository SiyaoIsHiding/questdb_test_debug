# SWE 261P Project Part 1: Introduction/ Set Up/ Functional Testing and Partitioning of QuestDB

**Team Member: Jane He, Fengnan Sun, Ming-Hua Tsai**

**Table of Contents**

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

![Untitled](SWE%20261P%20Project%20Part%201%20Introduction%20Set%20Up%20Functi%20c0dd92da19614f7d9820752672471ba0/Untitled.png)

![Untitled](SWE%20261P%20Project%20Part%201%20Introduction%20Set%20Up%20Functi%20c0dd92da19614f7d9820752672471ba0/Untitled%201.png)

## 1.2 **Important Concepts of QuestDB**

- **[Storage model](https://questdb.io/docs/concept/storage-model/)**
    - QuestDB uses a **column-based** storage model. Data is stored in tables with each column stored in its own file and its own native format. New data is appended to the bottom of each column to allow data to be organically retrieved in the same order that it was ingested.
    - The QuestDB storage model uses memory mapped files and cross-process atomic transaction updates as a low overhead method of inter-process communication. Data committed by one process can be instantaneously read by another process, either randomly (via queries) or incrementally (as a data queue). QuestDB provides a variety of reader implementations.

![Screen Shot 2023-02-03 at 4.00.56 PM.png](SWE%20261P%20Project%20Part%201%20Introduction%20Set%20Up%20Functi%20c0dd92da19614f7d9820752672471ba0/Screen_Shot_2023-02-03_at_4.00.56_PM.png)

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

![Untitled](SWE%20261P%20Project%20Part%201%20Introduction%20Set%20Up%20Functi%20c0dd92da19614f7d9820752672471ba0/Untitled%202.png)

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

![截屏2023-02-06 15.14.11.png](SWE%20261P%20Project%20Part%201%20Introduction%20Set%20Up%20Functi%20c0dd92da19614f7d9820752672471ba0/%25E6%2588%25AA%25E5%25B1%258F2023-02-06_15.14.11.png)

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

![截屏2023-02-06 16.56.45.png](SWE%20261P%20Project%20Part%201%20Introduction%20Set%20Up%20Functi%20c0dd92da19614f7d9820752672471ba0/%25E6%2588%25AA%25E5%25B1%258F2023-02-06_16.56.45.png)

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

![Screen Shot 2023-02-04 at 11.02.52 AM.png](SWE%20261P%20Project%20Part%201%20Introduction%20Set%20Up%20Functi%20c0dd92da19614f7d9820752672471ba0/Screen_Shot_2023-02-04_at_11.02.52_AM.png)

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