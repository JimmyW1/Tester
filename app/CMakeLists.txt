# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

link_directories(src/main/jni/ncca_native/lib/)

add_library( # Sets the name of the library.
             native-lib

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/jni/native-lib.c
             src/main/jni/ncca_native.c
             src/main/cpp/pos_api/smart_pos/pos_uart.c
             src/main/cpp/pos_api/smart_pos/pos_debug.c
             src/main/cpp/pos_api/smart_pos/pos_usbser.c
             src/main/cpp/pos_api/smart_pos/pos_charge.c
             src/main/cpp/j300_api/src/pinpad/j300.c
             src/main/cpp/j300_api/src/pinpad/j300_no_use.c
             src/main/cpp/j300_api/src/pinpad/j300_priv.c
             src/main/cpp/j300_api/src/pinpad/os_string.c
             src/main/cpp/j300_api/src/pinpad/PINPAD.c
             src/main/cpp/j300_api/src/pinpad/util.c
             src/main/cpp/j300_api/src/common/comm_driver.c
             src/main/cpp/j300_api/src/esign/esign.c
             src/main/cpp/j300_api/src/esign/esign_util.c
             src/main/cpp/j300_api/src/scanner/barcode.c
             # ncca test src code
             src/main/jni/ncca_native/sm_cli.c
             src/main/jni/ncca_native/sm_ssl.c
             src/main/jni/ncca_native/sm3.c
             src/main/jni/ncca_native/sm4.c
             src/main/jni/ncca_native/sm_lib.c
             src/main/jni/ncca_native/sm2.c
             src/main/jni/ncca_native/sm2_api.c
             src/main/jni/ncca_native/sm_api.c
             src/main/jni/ncca_native/sm2_cert.c
             # ncca wifi performance native
             src/main/jni/ncca_native/wifi_perf_client.c
             )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                       native-lib

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib}
                       crypto
                      )


include_directories(
        # j300 test inc
        src/main/cpp/pos_api/inc
        src/main/cpp/pos_api/mem_pool/cfg
        src/main/cpp/pos_api/mem_pool/inc
        src/main/cpp/pos_api/type/
        src/main/cpp/j300_api/inc/
        # ncca test inc
        src/main/jni/ncca_native/
        )
