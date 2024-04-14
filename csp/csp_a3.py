from constraint import Problem


def solve():
    ### Init
    big_rect_width = 7
    big_rect_height = 8

    rectangles = [
        (6, 4),  # rechteck 0
        (5, 2),  # ... 1
        (2, 2),  # ... 2
        (3, 2),  # ... 3
        (8, 1),  # ... 4
        (4, 1)  # ... 5
    ]

    problem = Problem()

    ### variablen
    variables = []

    for i, (width, height) in enumerate(rectangles):
        max_x = big_rect_width - min(width, height)  # Maximal x, unter Berücksichtigung der Drehung
        max_y = big_rect_height - min(width, height)  # Maximal y, ebenso
        x_var = f"x{i}"
        y_var = f"y{i}"
        r_var = f"r{i}"
        variables.append((x_var, y_var, r_var, width, height))

        problem.addVariable(x_var, range(max_x + 1))
        problem.addVariable(y_var, range(max_y + 1))
        problem.addVariable(r_var, [0, 1])

    # Notwendige Hilfsfunktion, da sich lambda nicht unmittelbar mit den Werten aus der for loop initialisieren lässt
    # Warum auch immer
    def create_constraint(a, b, c, d):
        return lambda x1, y1, r1, x2, y2, r2: not (
            ((x1 <= x2 < x1 + (a if r1 == 0 else b) or x2 <= x1 < x2 + (c if r2 == 0 else d)) and
             (y1 <= y2 < y1 + (b if r1 == 0 else a) or y2 <= y1 < y2 + (d if r2 == 0 else c)))
        )

    # Verifiziere - Alles innerhalb der Grenzen des Großen Rechtecks (Container)
    for x_var, y_var, r_var, width, height in variables:
        problem.addConstraint(lambda x, y, r, w=width, h=height, big_rect_w=big_rect_width, big_rect_h=big_rect_height:
                              (x + w <= big_rect_w and y + h <= big_rect_h) if r == 0 else (
                                          x + h <= big_rect_w and y + w <= big_rect_h),
                              (x_var, y_var, r_var))

    # Verifiziere keine Überlappungen zwischen kleinen Rechtecken im Container
    for i, (xi, yi, ri, wi, hi) in enumerate(variables):
        for j, (xj, yj, rj, wj, hj) in enumerate(variables):
            if i < j:
                constraint_func = create_constraint(wi, hi, wj, hj)
                problem.addConstraint(constraint_func, (xi, yi, ri, xj, yj, rj))

    solution = problem.getSolutions()
    return solution


if __name__ == '__main__':
    solutions = solve()
    print(f"Anzahl der Lösungen: {len(solutions)}")
    for s in solutions:
        print(s)
