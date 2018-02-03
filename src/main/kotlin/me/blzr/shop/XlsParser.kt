package me.blzr.shop

import me.blzr.shop.domain.Category
import me.blzr.shop.domain.Item
import me.blzr.shop.domain.Supplier
import me.blzr.shop.domain.Unit
import me.blzr.shop.repository.Categories
import me.blzr.shop.repository.Items
import me.blzr.shop.repository.Suppliers
import me.blzr.shop.repository.Units
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.InputStream
import java.math.BigDecimal

@Component
class XlsParser @Autowired constructor(
        private val items: Items,
        private val categories: Categories,
        private val suppliers: Suppliers,
        private val units: Units) {
    private val log = LoggerFactory.getLogger(XlsParser::class.java)

    fun cleanup(){
        items.deleteAll()
        categories.deleteAll()
        suppliers.deleteAll()
        units.deleteAll()
    }

    fun parse(stream: InputStream): Model {
        val m = Model()
        log.info("Begin XLS parsing")
        stream.use {
            val wb = HSSFWorkbook(stream)
            (1 until wb.numberOfSheets).map { wb.getSheetAt(it) }.forEach { sheet ->
                log.debug("Parsing ${sheet.sheetName}")
                (1..sheet.lastRowNum).map { sheet.getRow(it) }.forEachIndexed { index, row ->
                    try {
                        m.itemList.add(items.save(Item(
                                supplier = getSupplierOrSave(m, sheet.sheetName),
                                // TODO ignore for now Артикул A 0
                                category = getCategoryOrSave(m, row.getCell(1).stringCellValue), // Категория B 1
                                group = row.getCell(2).stringCellValue,// Группа C 2
                                name = row.getCell(3).stringCellValue,// Наименование D 3
                                price = BigDecimal(row.getCell(4).numericCellValue),// Цена за 1 ед. E 4
                                unit = getUnitOrSave(m, row.getCell(5).stringCellValue), // Ед. Изм. F 5
                                pack = BigDecimal(row.getCell(6)?.numericCellValue ?: 0.0) // Кол-во в упаковке G 6
                        )))
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

    private fun getSupplierOrSave(model: Model, name: String?) =
            model.supplierMap.computeIfAbsent(name ?: "ERROR") { suppliers.save(Supplier(name = it)) }

    private fun getUnitOrSave(model: Model, name: String?) =
            model.unitMap.computeIfAbsent(name ?: "ERROR") { units.save(Unit(name = it)) }

    private fun getCategoryOrSave(model: Model, name: String?) =
            model.categoryMap.computeIfAbsent(name ?: "ERROR") { categories.save(Category(name = it)) }

    class Model(
            val supplierMap: MutableMap<String, Supplier> = HashMap(),
            val unitMap: MutableMap<String, Unit> = HashMap(),
            val categoryMap: MutableMap<String, Category> = HashMap(),
            val itemList: MutableList<Item> = ArrayList())
}
