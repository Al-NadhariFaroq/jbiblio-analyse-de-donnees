<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
</head>
<body>
<h1>Panda Library</h1>
<p align="center">
<img alt="GitHub contributors" src="https://img.shields.io/github/contributors/Al-NadhariFaroq/jbiblio-analyse-de-donnees?style=plastic">
<img alt="Website" src="https://img.shields.io/website?down_color=lightgrey&down_message=down&style=plastic&up_color=blue&up_message=up&url=https%3A%2F%2Fgithub.com%2FAl-NadhariFaroq%2Fjbiblio-analyse-de-donnees">
<img alt="GitHub Workflow Status (with branch)" src="https://img.shields.io/github/actions/workflow/status/Al-NadhariFaroq/jbiblio-analyse-de-donnees/maven.yml?branch=main&style=plastic">
<img alt="Coveralls branch" src="https://img.shields.io/badge/coverage-98%25-brightgreen?style=plastic">
<img alt="GitHub top language" src="https://img.shields.io/github/languages/top/Al-NadhariFaroq/jbiblio-analyse-de-donnees?branch=main&style=plastic">
<img alt="GitHub repo file count (custom path)" src="https://img.shields.io/github/directory-file-count/Al-NadhariFaroq/jbiblio-analyse-de-donnees/src/main/java/fr/uga/bib?style=plastic">
<img alt="GitHub repo size" src="https://img.shields.io/github/repo-size/Al-NadhariFaroq/jbiblio-analyse-de-donnees?style=plastic">
<img alt="GitHub code size in bytes" src="https://img.shields.io/github/languages/code-size/Al-NadhariFaroq/jbiblio-analyse-de-donnees?style=plastic">
<img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Al-NadhariFaroq/jbiblio-analyse-de-donnees?style=plastic">
<img alt="GitHub commit activity" src="https://img.shields.io/github/commit-activity/w/Al-NadhariFaroq/jbiblio-analyse-de-donnees?style=plastic">
<img alt="GitHub closed pull requests" src="https://img.shields.io/github/issues-pr-closed/Al-NadhariFaroq/jbiblio-analyse-de-donnees?style=plastic">
<img alt="GitHub" src="https://img.shields.io/github/license/Al-NadhariFaroq/jbiblio-analyse-de-donnees?style=plastic">
</p>
<p>The Panda Library is a Java library for working with data frames.</p>
<h2>Features</h2>
<ul>
<li>Create data frames from 2D arrays or CSV files.</li>
<li>Display of the selecting columns, rows, or entire data frame.</li>
<li>Create sub data frames by selecting columns, rows, or by applying conditions.</li>
<li>Compute statistics for columns such as minimum, maximum, median, and sum.</li>
</ul>
<h2>Getting Started</h2>
<h3>From arrays</h3>
<p>To create a new data frame by passing in a 2D array:</p>
<pre>
Object[] col1 = {"Name", "string", "John", "Sarah"};
Object[] col2 = {"Age", "int", 25, 32};
Object[] col3 = {"Genre", "string", "Male", "Female"};
Object[][] data = {col1, col2, col3};
DataFrame frame = new DataFrame(data);
</pre>
<p>The first and second values of the tables must be the label of the column and the type of the column values respectively.<br />The following values must be of the given type or convertible to that type.</p>
<h3>From CSV files</h3>
<p>To create a new data frame by passing in a CSV file path:</p>
<pre>
String filePath = "resources/data.csv";
DataFrame frame = new DataFrame(filePath);
</pre>
<p>with the content of the <code>resources/data.csv</code> file:</p>
<pre>
Name,Age,Genre
string,int,string
John,25,Male
Sarah,32,Female
</pre>
<p>The first and second values of the columns in the file must be the label of the column and the type of the column values respectively.<br />The following values must be of the given type or convertible to that type.</p>
<h2>Supported types</h2>
<p>The types supported by data frames are the basic Java types (case-insensitive):</p>
<ul>
<li><code>Boolean</code> or <code>Bool</code></li>
<li><code>Byte</code></li>
<li><code>Short</code></li>
<li><code>Integer</code> or <code>Int</code></li>
<li><code>Long</code></li>
<li><code>Float</code></li>
<li><code>Double</code></li>
<li><code>Character</code> or <code>Char</code></li>
<li><code>String</code></li>
</ul>
<h2>Documentation</h2>
<p>For full documentation of the Panda Library, please refer to the <a href="https://al-nadharifaroq.github.io/jbiblio-analyse-de-donnees/apidocs/fr/uga/bib/DataFrame.html">API documentation</a>.</p><h2>License</h2>
<p>The Panda Library is licensed under the <a href="https://github.com/Al-NadhariFaroq/jbiblio-analyse-de-donnees/blob/main/LICENSE">UGA license</a>.</p>
</body>
</html>
