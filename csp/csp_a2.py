from constraint import Problem


def solve():
    # variables
    colors = ['red', 'blue', 'green']
    counties = ["Baden-Württemberg", "Bayern", "Berlin", "Brandenburg", "Bremen", "Hamburg", "Hessen",
                "Mecklenburg-Vorpommern", "Niedersachsen", "Nordrhein-Westfalen", "Rheinland-Pfalz", "Saarland", "Sachsen",
                "Sachsen-Anhalt", "Schleswig-Holstein", "Thüringen"]
    borders = {
        "Baden-Württemberg": ["Bayern", "Hessen", "Rheinland-Pfalz"],  # Baden-Württemberg
        "Bayern": ["Baden-Württemberg", "Hessen", "Thüringen"],  # Bayern
        "Berlin": ["Brandenburg"],  # Berlin
        "Brandenburg": ["Berlin", "Mecklenburg-Vorpommern", "Sachsen", "Sachsen-Anhalt"],  # Brandenburg
        "Bremen": ["Niedersachsen"],  # Bremen
        "Hamburg": ["Schleswig-Holstein"],  # Hamburg
        "Hessen": ["Baden-Württemberg", "Bayern", "Niedersachsen", "Nordrhein-Westfalen", "Rheinland-Pfalz",
                   "Thüringen"],  # Hessen
        "Mecklenburg-Vorpommern": ["Brandenburg", "Schleswig-Holstein"],  # Mecklenburg-Vorpommern
        "Niedersachsen": ["Bremen", "Hessen", "Hamburg", "Nordrhein-Westfalen", "Schleswig-Holstein", "Sachsen-Anhalt",
                          "Thüringen"],  # Niedersachsen
        "Nordrhein-Westfalen": ["Hessen", "Niedersachsen", "Rheinland-Pfalz"],  # Nordrhein-Westfalen
        "Rheinland-Pfalz": ["Baden-Württemberg", "Hessen", "Nordrhein-Westfalen", "Saarland"],  # Rheinland-Pfalz
        "Saarland": ["Rheinland-Pfalz"],  # Saarland
        "Sachsen": ["Brandenburg", "Sachsen-Anhalt", "Thüringen"],  # Sachsen
        "Sachsen-Anhalt": ["Brandenburg", "Niedersachsen", "Sachsen", "Thüringen"],  # Sachsen-Anhalt
        "Schleswig-Holstein": ["Hamburg", "Mecklenburg-Vorpommern", "Niedersachsen"],  # Schleswig-Holstein
        "Thüringen": ["Bayern", "Hessen", "Niedersachsen", "Sachsen", "Sachsen-Anhalt"]  # Thüringen
    }
    problem = Problem()
    problem.addVariables(counties, colors)

    for county, neighbors in borders.items():
        for neighbor in neighbors:
            problem.addConstraint(lambda x, y: x != y, (county, neighbor))

    solution = problem.getSolutions()
    return solution


if __name__ == "__main__":
    solutions = solve()
    print(f"Anzahl der Lösungen: {len(solutions)}")
    for s in solutions:
        print(s)
