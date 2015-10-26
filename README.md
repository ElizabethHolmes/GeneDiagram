# Gene_diagram
## About
GeneDiagram is a Java program for drawing linear diagrams of genes coloured by category. The categories can be of any type e.g. functional categories, such as those provided by the [Gene Ontology Consortium](http://geneontology.org/), or other user-defined categories, such types of differences in the genes vs a reference genome (SNPs, frameshift, etc.). If "pseudogene" is one of the categories, genes of this category are coloured white.

** PLEASE NOTE: GeneDiagram is one of my first Java programs written as a beginner Java programmer and so I apologise if the code is inelegant, unconventional or otherwise sub-optimal; it works for the intended purpose and I provide it in case it might be useful to others, but with no guarantees. **

## Citation
GeneDiagram is not associated with a paper; to cite it please use:

    Sutton, ER. (2015). GeneDiagram [Software]. 
    Available at https://github.com/ElizabethSutton/GeneDiagram.

## Requirements
GeneDiagram requires a Java Runtime Environment.

## Usage
GeneDiagram takes as its input a tab-delimited text file, such as the 'genes.txt' file provided, comprising a list of genes and their attributes. The first column contains the gene name, the second the category, the third the strand (forward or reverse) on which the gene is located, the fourth the start co-ordinate of the gene, and the fifth the stop co-ordinate of the gene. The example input file provided, 'genes.txt', looks like this:

 | | | | |
--- | --- | --- | --- | ---
gene1 | category1 | + | 200 | 300
gene2 | category2 | - | 300 | 400
gene3 | category3 | + | 600 | 700
gene4 | category4 | + | 800 | 900
gene5 | category1 | - | 1000 | 1200
gene6 | category5 | + | 1300 | 1400
gene7 | category6 | + | 1500 | 1700
gene8 | pseudogene | - | 1800 | 2000

### Example
To create a diagram illustrating the genes described in the example input file provided, 'genes.txt', the command would be:

  java -jar GeneDiagram.jar
  
## Output
GeneDiagram generates a JPEG file illustrating the genes coloured by category and with a legend. The example above produces the example diagram provided, 'genes.jpg'.


