def call(String world) {
    echo "param world: ${world}"
    def result = sh(returnStdout: true, script: "helloworld.sh ${world}")
    echo "RESULT" + result
    return result
}

/**
 * Returns the path to a temp location of a script from the global library (resources/ subdirectory)
 *
 * @param srcPath path within the resources/ subdirectory of this repo
 * @param destPath destination path (optional)
 * @return path to local file
 */
def copyGlobalLibraryScript(String srcPath, String destPath = null) {
    writeFile file: destPath, text: libraryResource(srcPath)
    echo "copyGlobalLibraryScript: copied ${srcPath} to ${destPath}"
    return destPath
}

