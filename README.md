# Sensor-Data-Using-AIDL-IMU
#### Sensor data using AIDL With Services.


|  No. | APK Name  | Download Link.  | Version No.|
| ------------ | ------------ | ------------ |------------|
|   1.     | **IMU Data** |  <button>[Download](https://github.com/Dinesh2510/Sensor-Data-Using-AIDL-IMU/raw/master/app-debug.apk) </button> | **1.0** |


# Android Interface Definition Language (AIDL)

The Android Interface Definition Language (AIDL) is similar to other IDLs you might have worked with.
It allows you to define the programming interface that both the client and service agree upon in order to communicate with each other using interprocess communication (IPC). 

# Sensor.TYPE_ROTATION_VECTOR

#### SensorEvent.values[0]	Rotation vector component along the x axis (x * sin(θ/2)).	Unitless
#### SensorEvent.values[1]	Rotation vector component along the y axis (y * sin(θ/2)).
#### SensorEvent.values[2]	Rotation vector component along the z axis (z * sin(θ/2)).
#### SensorEvent.values[3]	Scalar component of the rotation vector ((cos(θ/2)).1
