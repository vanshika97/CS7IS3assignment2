package ie.tcd.cs7is3.documents;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utils {

    public void deleteDir(File file) {

        File[] contents = file.listFiles();
        if (contents != null) {

            for (File f : contents) {

                deleteDir(f);
            }
        }
        file.delete();
    }

    public void checkIfDirectory(String path) {

        File docsDir = new File(path);
        if (!docsDir.isDirectory()) {

            System.out.println(
                    path + " is not a directory. Please input correct arguments, or run with default no arguments.");
            System.out.println("Exiting application.");
            System.exit(1);
        }
    }

    public void checkIfFile(String path) {

        File ftDocsFile = new File(path);
        if (!ftDocsFile.isFile()) {

            System.out.println(path + " is not present. Please ensure parsed JSON is present in the directory.");
            System.out.println("Exiting application.");
            System.exit(1);
        }
    }
    public String refineDirectoryString(String directoryString) {

        directoryString = directoryString.replace("\\", "/");
        if (!directoryString.endsWith("/"))
            directoryString += "/";
        return directoryString;
    }

    public List<File> getFiles(String directoryStr, boolean hasSubDirectory) {

        List<File> filesList = new ArrayList<File>();
        File directory = new File(directoryStr);

        // Check if argument specified is a directory
        if (directory.isDirectory()) {

            // List all sub-directories
            String[] subDirectories = directory.list(new FilenameFilter() {

                @Override
                public boolean accept(File dir, String name) {

                    File newFile = new File(dir, name);
                    if (hasSubDirectory) {

                        // Only return files which is a directory
                        return newFile.isDirectory();
                    } else {

                        // Only return files which is not a directory
                        if (!newFile.isDirectory()) {

                            filesList.add(newFile);
                            return true;
                        } else
                            return false;
                    }
                }
            });

            if (hasSubDirectory) {

                for (String subDirectoryStr : subDirectories) {

                    File subDirectory = new File(directoryStr + subDirectoryStr);
                    // List all files inside the sub-directory
                    subDirectory.list(new FilenameFilter() {

                        @Override
                        public boolean accept(File dir, String name) {

                            File newFile = new File(dir, name);
                            // Only return files which is not a directory
                            if (!newFile.isDirectory()) {

                                filesList.add(newFile);
                                return true;
                            } else
                                return false;
                        }
                    });
                }
            }
        } else {

            System.out.println(directory + " is not a directory.");
            System.out.println("Exiting application.");
            System.exit(1);
        }

        return filesList;
    }

    public void storeContentInMap(Map<String, String> map, String line, String element) {

        String currentHeadline = map.get(element.toLowerCase());
        if (currentHeadline == null)
            currentHeadline = line + " ";
        else
            currentHeadline += line + " ";
        map.put(element.toLowerCase(), currentHeadline);
    }
}
