package com.test.util;

import android.util.Log;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

/**
 * Created by CuncheW1 on 2017/9/18.
 */

public class FileUtil {
    private static final String TAG = "FileUtil";


    public static void Unzip(InputStream inputStream, String targetDir) {

        if (inputStream == null || targetDir == null || targetDir.length() == 0) {
            return;
        }

        try {
            ArchiveInputStream archiveInputStream = new ArchiveStreamFactory().createArchiveInputStream("zip", inputStream);
            ArchiveEntry archiveEntry = null;

            while ((archiveEntry = archiveInputStream.getNextEntry()) != null) {
                String entryFileName = archiveEntry.getName();
                String entryFilePath = targetDir + File.separator + entryFileName;
                LogUtil.d(TAG, "Entry file name=" + entryFilePath);
                File saveFile = new File(entryFilePath).getParentFile();
                if (!saveFile.exists()) {
                    saveFile.mkdirs();
                }

                OutputStream os = new FileOutputStream(entryFilePath);
                long size = 0;
                long entryFileSize = archiveEntry.getSize();
                LogUtil.d(TAG, "Entry file size=" + entryFileSize);
                while (size < archiveEntry.getSize()) {
                    int coptyBlockSize = 4096;
                    if (entryFileSize - size < 4096) {
                        coptyBlockSize = (int) (entryFileSize - size);
                    }

                    IOUtils.copy(archiveInputStream, os, coptyBlockSize);
                    size += coptyBlockSize;
                }
            }
       } catch (Exception cwj) {
            cwj.printStackTrace();
        }
    }

}
