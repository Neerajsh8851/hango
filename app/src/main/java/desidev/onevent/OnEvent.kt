package desidev.onevent

import desidev.hango.api.model.UserCredential

fun interface OnEmailUpdate { fun updateEmail(value: String) }

fun interface OnPasswordUpdate { fun updatePassword(value: String) }

fun interface OnSignIn { fun onSignIn(credential: UserCredential) }
