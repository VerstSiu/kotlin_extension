module.exports = function() {
  var projectUrl = "https://github.com/VerstSiu/kotlin_extension"

  return {
    types: [
      {type: "feat", section: "New Features"},
      {type: "fix", section: "Bug Fixes"},
      {type: "chore", section: "Chore", hidden: true},
      {type: "docs", section: "Document Changes", hidden: true},
      {type: "style", section: "Style Changes", hidden: true},
      {type: "refactor", section: "Refactor", hidden: true},
      {type: "perf", section: "Performance Upgrade"},
      {type: "test", section: "Test", hidden: true},
      {type: "ci", section: "CI Configurations", hidden: true},
      {type: "revert", section: "Reverts", hidden: true}
    ],
    bumpFiles: [
        {
          filename: "build.gradle",
          updater: "build-updater.js"
        },
        {
          filename: "package.json",
          type: "json"
        }
      ],

    commitUrlFormat: projectUrl + "commit/{{hash}}",
    compareUrlFormat: projectUrl + "compare/{{previousTag}}...{{currentTag}}",
    issueUrlFormat: projectUrl + "issues/{{id}}",
    userUrlFormat: projectUrl + "{{user}}"
  };
}