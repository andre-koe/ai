from constraint import Problem


def solve():
    problem = Problem()

    # Variablen für Lehrer, Fächer und Räume hinzufügen
    teachers = ['Maier', 'Müller', 'Schmid', 'Huber']
    subjects = ['Deutsch', 'Mathe', 'Physik', 'Englisch']
    rooms = [1, 2, 3, 4]

    for teacher in teachers:
        problem.addVariable(teacher, [(subject, room) for subject in subjects for room in rooms])

    problem.addConstraint(lambda x, y, z, w: len({x[0], y[0], z[0], w[0]}) == 4, teachers)  # Eindeutige Fächer
    problem.addConstraint(lambda x, y, z, w: len({x[1], y[1], z[1], w[1]}) == 4, teachers)  # Eindeutige Räume

    # Constraints für Lehrer-Fach-Zuordnungen
    problem.addConstraint(lambda p: p[0] == 'Deutsch', ['Müller'])  # Herr Müller prüft immer Deutsch
    problem.addConstraint(lambda p: p[0] == 'Mathe', ['Huber'])  # Frau Huber prüft Mathematik
    problem.addConstraint(lambda p: p[1] != 4, ['Maier'])  # Maier nicht in 4
    problem.addConstraint(lambda p1, p2: abs(p1[1] - p2[1]) > 1, ['Müller', 'Schmid'])  # Müller Schmid nicht benachbart

    for teacher in teachers:
        problem.addConstraint(lambda p: p[1] == 4 if p[0] == 'Physik' else True, [teacher])  # Physik nur in 4
        problem.addConstraint(lambda p: p[1] != 1 if p[0] == 'Englisch' or p[0] == 'Deutsch' else True, [teacher])  # Deutsch und Englisch nicht in 1

    problem.addConstraint(lambda d, m, p, e: len({d, m, p, e}) == 4, teachers)
    return problem.getSolutions()


if __name__ == "__main__":
    solutions = solve()
    for solution in solutions:
        print(solution)

    print(f"Anzahl Lösungen: {len(solutions)}")
