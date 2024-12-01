var pow := func(n, p) is var i := 1
    var res := n
    while i < p loop res := res * n
        i := i + 1
    end
    return res * 1.0
end 

var split := func(str, del) is var res := []
    var temp := []
    var i
    for i in chars(str) loop if i = del then if len(temp) != 0 then res := res + [fromChars(temp)]
            temp := []
        end
        else temp := temp + [i]
        end
    end
    if len(temp) != 0 then return res + [fromChars(temp)]
    else return res
    end
end

var parseDigit := func(str) is if len(str) != 1 then return error("parseDigit: length of the string should be 1"); end
    if str = "1" then return 1; end
    if str = "2" then return 2; end
    if str = "3" then return 3; end
    if str = "4" then return 4; end
    if str = "5" then return 5; end
    if str = "6" then return 6; end
    if str = "7" then return 7; end
    if str = "8" then return 8; end
    if str = "9" then return 9; end
    if str = "0" then return 0; end
    return error("parseDigit: input is not a digit")
end

var parseInt := func(str) is var res := 0
    var chs := chars(str)
    var flag := false
    if chs[1] = "-" then flag := true
        chs[1] := "0"
    end

    var ch
    for ch in chs loop var digit := parseDigit(ch)
        res := res * 10 + digit
    end

    if flag then res := res * (-1); end
    return res
end

var parseDouble := func (str) is var res := 0
    var parts := split(str, ".")
    if len(parts) != 2 then return error("parseDouble: incorrect number of dots in the string, expected 1"); end
    var left := parseInt(parts[1])
    var right := parseInt(parts[2])/pow(10, len(parts[2]))
    return left + right
end

print parseInt("-123") + 23
print parseDouble("123.456") + 0.0007

var s := read()
print split(s, "l")

var i := readInt()
var d := readDouble()
print i + d

