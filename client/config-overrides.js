const {
    override,
    fixBabelImports,
    addLessLoader,
    addWebpackAlias,
    overrideDevServer
} = require("customize-cra");

const path = require('path')


// 跨域配置
const devServerConfig = () => config => {
    return {
        ...config,
    }
}

module.exports = {
    webpack: override(
        fixBabelImports("import", {
            libraryName: "antd", libraryDirectory: "es", style: true // change importing css to less
        }),
        addLessLoader({
            lessOptions: {
                javascriptEnabled: true,
                modifyVars: {
                    "@layout-body-background": "#FFFFFF",
                    "@layout-header-background": "#FFFFFF",
                    "@layout-footer-background": "#FFFFFF"
                }
            }
        }),
        addWebpackAlias({
            ["src"]: path.resolve(__dirname, "src"),
            ["@"]: path.resolve(__dirname, "src"),
            ["containers"]: path.resolve(__dirname, "src/containers"),
            ["components"]: path.resolve(__dirname, "src/app/components"),
            ["constants"]: path.resolve(__dirname, "src/app/constants"),
            ["util"]: path.resolve(__dirname, "src/app/util"),
            ["hooks"]: path.resolve(__dirname, "src/app/hooks")
        })
    )
}
