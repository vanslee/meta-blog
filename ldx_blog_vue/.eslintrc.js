module.exports = {
  root: true,

  env: {
    node: true
  },

  parserOptions: {
    parser: '@babel/eslint-parser'
  },

  rules: {
    'no-console': 'off',
    'no-debugger': 'off'
  },

  extends: [
    'plugin:vue/strongly-recommended',
    '@vue/standard'
  ]
}
