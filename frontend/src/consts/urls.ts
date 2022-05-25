//+-------------------------------------------------------------------------
//
//  值得铭记的 URL 们
//
//  File:       urls.ts
//
//  Directory:  src/consts
//
//  History:    4月-26-2022   QQK  Created
//
//--------------------------------------------------------------------------

const CDN_URL = '/static'

const VITE_BACKEND_URL = import.meta.env.DEV
  ? import.meta.env.VITE_BACKEND_URL as string
  : 'VITE_BACKEND_URL_RUNTIME_REPLACEMENT';

export {
  CDN_URL,
  VITE_BACKEND_URL
}
