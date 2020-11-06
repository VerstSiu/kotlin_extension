const stringifyPackage = require('stringify-package')
const detectIndent = require('detect-indent')
const detectNewline = require('detect-newline')

module.exports.readVersion = function (contents) {
  var pattern = /\bversionName\s?=\s?['"](.*)['"]/
  String(contents).match(pattern)
  return RegExp.$1
}

module.exports.writeVersion = function (contents, version) {
  var reg = /(\bversionName\s?=\s?)['"](.*)['"]/
  return contents.replace(reg, `$1\"${version}\"`)
}