package utils

func RemovePrefix(prefix, str string) string {
	if len(prefix) == 0 {
		return str
	}
	if str == prefix {
		return ""
	}
	if str[:len(prefix)] == prefix {
		return str[len(prefix):]
	}
	return str
}
