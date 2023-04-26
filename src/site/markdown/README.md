
# Panda Library

[![GitHub contributors](https://github.com/Al-NadhariFaroq/jbiblio-analyse-de-donnees/graphs/contributors)](https://img.shields.io/github/contributors/Al-NadhariFaroq/jbiblio-analyse-de-donnees?style=plastic)
[![Website](https://al-nadharifaroq.github.io/jbiblio-analyse-de-donnees/index.html)](https://img.shields.io/website?down_color=lightgrey&down_message=down&style=plastic&up_color=blue&up_message=up&url=https%3A%2F%2Fgithub.com%2FAl-NadhariFaroq%2Fjbiblio-analyse-de-donnees)
[![GitHub Workflow Status](https://github.com/Al-NadhariFaroq/jbiblio-analyse-de-donnees)](https://img.shields.io/github/actions/workflow/status/Al-NadhariFaroq/jbiblio-analyse-de-donnees/maven.yml?branch=main&style=plastic)
[![Coveralls branch](https://github.com/Al-NadhariFaroq/jbiblio-analyse-de-donnees)](https://img.shields.io/badge/coverage-98%25-brightgreen?style=plastic)
[![GitHub top language](https://github.com/Al-NadhariFaroq/jbiblio-analyse-de-donnees/search?l=java)](https://img.shields.io/github/languages/top/Al-NadhariFaroq/jbiblio-analyse-de-donnees?branch=main&style=plastic)
[![GitHub repo file count](https://github.com/Al-NadhariFaroq/jbiblio-analyse-de-donnees/tree/main/src/main/java/fr/uga/bib)](https://img.shields.io/github/directory-file-count/Al-NadhariFaroq/jbiblio-analyse-de-donnees/src/main/java/fr/uga/bib?style=plastic)
[![GitHub repo size](https://github.com/Al-NadhariFaroq/jbiblio-analyse-de-donnees)](https://img.shields.io/github/repo-size/Al-NadhariFaroq/jbiblio-analyse-de-donnees?style=plastic)
[![GitHub code size in bytes](https://github.com/Al-NadhariFaroq/jbiblio-analyse-de-donnees)](https://img.shields.io/github/languages/code-size/Al-NadhariFaroq/jbiblio-analyse-de-donnees?style=plastic)
[![Docker Image Size](https://hub.docker.com/r/f1r09/jbiblio)](https://img.shields.io/docker/image-size/f1r09/jbiblio?style=plastic)
[![GitHub last commit](https://github.com/Al-NadhariFaroq/jbiblio-analyse-de-donnees/graphs/commit-activity)](https://img.shields.io/github/last-commit/Al-NadhariFaroq/jbiblio-analyse-de-donnees?style=plastic)
[![GitHub commit activity](https://github.com/Al-NadhariFaroq/jbiblio-analyse-de-donnees/graphs/commit-activity)](https://img.shields.io/github/commit-activity/w/Al-NadhariFaroq/jbiblio-analyse-de-donnees?style=plastic)
[![GitHub closed pull requests](https://github.com/Al-NadhariFaroq/jbiblio-analyse-de-donnees/pulls?q=is%3Apr+is%3Aclosed)](https://img.shields.io/github/issues-pr-closed/Al-NadhariFaroq/jbiblio-analyse-de-donnees?style=plastic)
[![GitHub License](https://github.com/Al-NadhariFaroq/jbiblio-analyse-de-donnees/blob/main/LICENSE)](https://img.shields.io/github/license/Al-NadhariFaroq/jbiblio-analyse-de-donnees?style=plastic)

The Panda Library is a Java library for working with data frames.

## Features

- Create data frames from 2D arrays or CSV files.
- Display of the selecting columns, rows, or entire data frame.
- Create sub data frames by selecting columns, rows, or by applying conditions.
- Compute statistics for columns such as minimum, maximum, median, and sum.

## Getting Started

### From arrays

To create a new data frame by passing in a 2D array:

```java
Object[] col1 = {"Name", "string", "John", "Sarah"};
Object[] col2 = {"Age", "int", 25, 32};
Object[] col3 = {"Genre", "string", "Male", "Female"};
Object[][] data = {col1, col2, col3};
DataFrame frame = new DataFrame(data);
```

The first and second values of the tables must be the label of the column and the type of the column values respectively.
The following values must be of the given type or convertible to that type.

### From CSV files

To create a new data frame by passing in a CSV file path:

```java
String filePath = "resources/data.csv";
DataFrame frame = new DataFrame(filePath);
```

with the content of the `resources/data.csv` file:

```java
Name,Age,Genre
string,int,string
John,25,Male
Sarah,32,Female
```

The first and second values of the columns in the file must be the label of the column and the type of the column values respectively.
The following values must be of the given type or convertible to that type.

## Supported types

The types supported by data frames are the basic Java types (case-insensitive):
- `Boolean` or `Bool`
- `Byte`
- `Short`
- `Integer` or `Int`
- `Long`
- `Float`
- `Double`
- `Character` or `Char`
- `String`

## Documentation

For full documentation of the Panda Library, please refer to the [API documentation](https://al-nadharifaroq.github.io/jbiblio-analyse-de-donnees/apidocs/fr/uga/bib/DataFrame.html).

## License

The Panda Library is licensed under the [UGA license](https://github.com/Al-NadhariFaroq/jbiblio-analyse-de-donnees/blob/main/LICENSE).
