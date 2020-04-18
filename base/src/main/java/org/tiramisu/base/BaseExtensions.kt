package org.tiramisu.base

import android.app.Application
import kotlin.properties.Delegates

var appContext: Application by Delegates.notNull()