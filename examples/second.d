var a := 5
var b := a is int
print a, b
a := "string"
b := a is string
print a, b

var c
var d := c is empty
print c, d

var x := 5
for aa in 1 .. 6 loop x := aa * 2 + x
    print x
end

var arr := [1, 2, 3]
var tup := {a:= 5, true}
print arr[1]
print tup.1

var f := func (x) is return x * x
end

for i in [9, 1, 4, 7] loop print f(i)
end

print arr is []

var t := {a:= 3, true, b := {false, func () => 25}}
print t
