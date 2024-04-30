from constraint import Problem


def solve():
    # variables
    colors = ['red', 'blue', 'green']
    counties = ["Baden-Württemberg", "Bayern", "Berlin", "Brandenburg", "Bremen", "Hamburg", "Hessen",
                "Mecklenburg-Vorpommern", "Niedersachsen", "Nordrhein-Westfalen", "Rheinland-Pfalz", "Saarland", "Sachsen",
                "Sachsen-Anhalt", "Schleswig-Holstein", "Thüringen"]
    borders = {
        'Baden-Württemberg': ['Bayern', 'Hessen', 'Rheinland-Pfalz', 'Saarland'],
        'Bayern': ['Baden-Württemberg', 'Hessen', 'Thüringen', 'Sachsen'],
        'Berlin': ['Brandenburg'],
        'Brandenburg': ['Berlin', 'Mecklenburg-Vorpommern', 'Niedersachsen', 'Sachsen-Anhalt', 'Sachsen'],
        'Bremen': ['Niedersachsen'],
        'Hamburg': ['Niedersachsen', 'Schleswig-Holstein'],
        'Hessen': ['Baden-Württemberg', 'Bayern', 'Nordrhein-Westfalen', 'Rheinland-Pfalz', 'Thüringen',
                   'Niedersachsen'],
        'Mecklenburg-Vorpommern': ['Brandenburg', 'Niedersachsen', 'Schleswig-Holstein'],
        'Niedersachsen': ['Brandenburg', 'Bremen', 'Hamburg', 'Mecklenburg-Vorpommern', 'Nordrhein-Westfalen',
                          'Schleswig-Holstein', 'Hessen', 'Sachsen-Anhalt'],
        'Nordrhein-Westfalen': ['Hessen', 'Niedersachsen', 'Rheinland-Pfalz'],
        'Rheinland-Pfalz': ['Baden-Württemberg', 'Hessen', 'Nordrhein-Westfalen', 'Saarland'],
        'Saarland': ['Baden-Württemberg', 'Rheinland-Pfalz'],
        'Sachsen': ['Sachsen-Anhalt', 'Thüringen', 'Bayern', 'Brandenburg'],
        'Sachsen-Anhalt': ['Brandenburg', 'Sachsen', 'Thüringen', 'Niedersachsen'],
        'Schleswig-Holstein': ['Hamburg', 'Mecklenburg-Vorpommern', 'Niedersachsen'],
        'Thüringen': ['Bayern', 'Hessen', 'Sachsen', 'Sachsen-Anhalt']
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
    for s in solutions:

        print(s)

    print(f"Anzahl der Lösungen: {len(solutions)}")

