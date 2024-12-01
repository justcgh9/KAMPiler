var abs := func(a) is if a > 0 then return a
    else return -a
    end
end

var sqrt := func(a) is var x := a * 0.5
    while true loop var x_n := (x + a * 1.0 /x) * 0.5
        if abs(x_n - x) < 0.01 then return x
        end
        x := x_n
    end
end

var euclideanDistance := func(a, b) => sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y))

var a := {x := 0, y := 0}

var b := {x := 1, y := 1}

print euclideanDistance(a, b)