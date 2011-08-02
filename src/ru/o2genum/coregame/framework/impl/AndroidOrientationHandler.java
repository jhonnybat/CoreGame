package ru.o2genum.coregame.framework.impl;

import android.hardware.*;
import android.content.Context;

public class AndroidOrientationHandler implements OrientationHandler,
		  SensorEventListener
{
	SensorManager manager;
	Sensor sensor;
	float azimuth = 0.0F;

	public AndroidOrientationHandler(Context context)
	{
		manager = 
			(SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		sensor = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
		// Nothing to do
	}

	public void onSensorChanged(SensorEvent event)
	{
		azimuth = event.values[0];
	}

	@Override
	public float getAzimuth()
	{
		return azimuth;
	}
}
