# MantraCount

This project is designed to count instances of mantra submissions through WhatsApp for the Associação Buddha-Dharma associates in Brazil. The application processes export text files from WhatsApp line by line and looks for strings formatted as: "Fiz # mantras de 'mantra name'", which translates to "I did # mantras of 'mantra name'".


## Features

- Reads WhatsApp export text and counts:
  - Total number of mantras submitted.
  - Instances of the word "Fiz".
  - Instances of the word "mantras".
  - Specific mantra names.
- Displays discrepancies with counts, allowing users to correct any errors in the original text and run the program again for an accurate count.

## Requirements

- Java (version X or higher)
- Any standard text editor or IDE for Java development (e.g., IntelliJ, Eclipse).

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/TashiRabten/MantraCount.git
## Codacy Grade
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/4f9fc568d06943a9826ca6c746f6f414)](https://app.codacy.com?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
