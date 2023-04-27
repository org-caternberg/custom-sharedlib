/**
 * Returns the path to a temp location of a script from the global library (resources/ subdirectory)
 *
 * @param srcPath path within the resources/ subdirectory of this repo
 * @param destPath destination path (optional)
 * @return path to local file
 */
def call(String srcPath, String destPath = ".") {
    writeFile file: destPath, text: libraryResource(srcPath)
    echo "copyFileFromLibraryResources: copied ${srcPath} to ${destPath}"
    return destPath
}