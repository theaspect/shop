package me.blzr.shop

import me.blzr.shop.domain.Category
import me.blzr.shop.domain.Item
import me.blzr.shop.domain.Supplier
import me.blzr.shop.domain.Unit
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.slf4j.LoggerFactory
import java.io.InputStream
import java.math.BigDecimal

object XlsParser {
    private val log = LoggerFactory.getLogger(XlsParser::class.java)
    fun parse(stream: InputStream): Model {
        val m = Model()
        log.info("Begin XLS parsing")
        stream.use {
            val wb = HSSFWorkbook(stream)
            (1 until wb.numberOfSheets).map { wb.getSheetAt(it) }.forEach { sheet ->
                log.debug("Parsing ${sheet.sheetName}")
                (1..sheet.lastRowNum).map { sheet.getRow(it) }.forEachIndexed { index, row ->
                    try {
                        m.items.add(Item(
                                supplier = m.supplier.getValue(sheet.sheetName),
                                // TODO ignore for now Артикул A 0
                                category = m.categories.getValue(row.getCell(1).stringCellValue), // Категория B 1
                                group = row.getCell(2).stringCellValue,// Группа C 2
                                name = row.getCell(3).stringCellValue,// Наименование D 3
                                price = BigDecimal(row.getCell(4).numericCellValue),// Цена за 1 ед. E 4
                                unit = m.units.getValue(row.getCell(5).stringCellValue), // Ед. Изм. F 5
                                pack = BigDecimal(row.getCell(6)?.numericCellValue ?: 0.0) // Кол-во в упаковке G 6
                        ))
                    } catch (e: Exception) {
                        log.error("Can't parse row ${index + 1}", e)
                    }
                }
                log.debug("Loaded ${sheet.lastRowNum - 1} rows")
            }
        }
        log.info("Finish XLS parsing")
        return m
    }

    class Model(
            val supplier: MutableMap<String?, Supplier> = HashMap<String?, Supplier>().withDefault { Supplier(name = it ?: "ERROR") },
            val units: MutableMap<String?, Unit> = HashMap<String?, Unit>().withDefault { Unit(name = it ?: "ERROR") },
            val categories: MutableMap<String?, Category> = HashMap<String?, Category>().withDefault { Category(name = it ?: "ERROR") },
            val items: MutableList<Item> = ArrayList())
}
